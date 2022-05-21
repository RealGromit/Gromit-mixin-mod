package com.gromit.gromitmod.network;

import com.gromit.gromitmod.event.network.InboundPacket;
import com.gromit.gromitmod.event.network.OutboundPacket;
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

        OutboundPacket outboundPacket = new OutboundPacket((Packet<?>) packet);
        MinecraftForge.EVENT_BUS.post(outboundPacket);

        if (outboundPacket.isCanceled()) return;
        super.write(ctx, outboundPacket.getPacket(), promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!(packet instanceof Packet<?>)) return;

        InboundPacket inboundPacket = new InboundPacket((Packet<?>) packet);
        MinecraftForge.EVENT_BUS.post(inboundPacket);

        if (inboundPacket.isCanceled()) return;
        super.channelRead(ctx, inboundPacket.getPacket());
    }
}
