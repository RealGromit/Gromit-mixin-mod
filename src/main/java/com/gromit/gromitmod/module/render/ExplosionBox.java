package com.gromit.gromitmod.module.render;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.event.network.InboundPacket;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.module.AbstractModule;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;

public class ExplosionBox extends AbstractModule {

    private final HashSet<AxisAlignedBBTime> boxSet = new HashSet<>();
    private BlockPos oldPos;
    private final Minecraft minecraft;

    public ExplosionBox(GromitMod gromitMod) {
        this.minecraft = gromitMod.getMinecraft();
    }

    @SubscribeEvent
    public void onPacketReceive(InboundPacket event) {
        if (!(event.getPacket() instanceof S27PacketExplosion)) return;
        S27PacketExplosion explosion = (S27PacketExplosion) event.getPacket();
        if (oldPos != null) {
            if (new BlockPos(explosion.getX(), explosion.getY(), explosion.getZ()).equals(oldPos)) return;
        }
        boxSet.add(new AxisAlignedBBTime(explosion.getX() - 0.25, explosion.getY(), explosion.getZ() - 0.25, explosion.getX() + 0.25, explosion.getY() + 0.5, explosion.getZ() + 0.25, System.currentTimeMillis()));
        oldPos = new BlockPos(explosion.getX(), explosion.getY(), explosion.getZ());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (boxSet.isEmpty()) return;
        long time = System.currentTimeMillis();
        int boxTimeout = ExplosionBoxGui.slider.currentValue * 1000;
        boxSet.removeIf(aabbTime -> time - aabbTime.getTime() > boxTimeout);
    }
}

class AxisAlignedBBTime extends AxisAlignedBB {

    private final long time;

    public AxisAlignedBBTime(double x1, double y1, double z1, double x2, double y2, double z2, long time) {
        super(x1, y1, z1, x2, y2, z2);
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}