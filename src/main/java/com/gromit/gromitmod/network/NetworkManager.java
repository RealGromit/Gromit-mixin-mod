package com.gromit.gromitmod.network;

import com.gromit.gromitmod.event.network.InboundPacketEvent;
import com.gromit.gromitmod.event.network.OutboundPacketEvent;
import io.netty.channel.*;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@ChannelHandler.Sharable
public class NetworkManager extends ChannelDuplexHandler {

    public NetworkManager() {MinecraftForge.EVENT_BUS.register(this);}

    @SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ChannelPipeline pipeline = event.manager.channel().pipeline();

        if (pipeline == null) return;
        if (pipeline.get("packet_handler") == null) return;
        if (pipeline.get("gromitmod") != null) return;
        pipeline.addBefore("packet_handler", "gromitmod", this);
        System.out.println(pipeline.toMap().toString());
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ChannelPipeline pipeline = event.manager.channel().pipeline();

        if (pipeline == null) return;
        if (pipeline.get("gromitmod") == null) return;
        pipeline.remove(this);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (!(packet instanceof Packet<?>)) return;

        OutboundPacketEvent outboundPacketEvent = new OutboundPacketEvent((Packet<?>) packet);
        MinecraftForge.EVENT_BUS.post(outboundPacketEvent);

        if (outboundPacketEvent.isCanceled()) return;
        super.write(ctx, outboundPacketEvent.getPacket(), promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!(packet instanceof Packet<?>)) return;

        InboundPacketEvent inboundPacketEvent = new InboundPacketEvent((Packet<?>) packet);
        MinecraftForge.EVENT_BUS.post(inboundPacketEvent);

        if (inboundPacketEvent.isCanceled()) return;
        super.channelRead(ctx, inboundPacketEvent.getPacket());
    }
}
