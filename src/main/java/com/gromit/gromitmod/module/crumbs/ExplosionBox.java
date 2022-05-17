package com.gromit.gromitmod.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.event.network.InboundPacket;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ExplosionBox extends AbstractModule {

    private static ExplosionBox instance;
    private final ColorButton colorButton = ExplosionBoxGui.boxColorButton;
    private final ColorButton outlineColorButton = ExplosionBoxGui.outlineColorButton;
    private final CheckboxButton precision = ExplosionBoxGui.precision;
    private final RenderManager renderManager;
    private final Set<AxisAlignedBBTime> boxSet = ConcurrentHashMap.newKeySet();
    private final Minecraft minecraft;

    public ExplosionBox(GromitMod gromitMod) {
        instance = this;
        renderManager = gromitMod.getMinecraft().getRenderManager();
        minecraft = gromitMod.getMinecraft();
    }

    @SubscribeEvent
    public void onPacketReceive(InboundPacket event) {
        if (!(event.getPacket() instanceof S27PacketExplosion)) return;
        S27PacketExplosion explosion = (S27PacketExplosion) event.getPacket();
        AxisAlignedBBTime box;
        if (precision.isState()) box = new AxisAlignedBBTime(explosion.getX() - 0.1, explosion.getY() + 0.4 - 0.06125, explosion.getZ() - 0.1, explosion.getX() + 0.1, explosion.getY() + 0.6 - 0.06125, explosion.getZ() + 0.1, System.currentTimeMillis());
        else {
            double posX = Math.floor(explosion.getX());
            double posY = Math.floor(explosion.getY());
            double posZ = Math.floor(explosion.getZ());
            box = new AxisAlignedBBTime(posX + 0.4, posY + 0.4, posZ + 0.4, posX + 0.6, posY + 0.6, posZ + 0.6, System.currentTimeMillis());
        } boxSet.add(box);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (boxSet.isEmpty()) return;
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.phase != TickEvent.Phase.START) return;
        long time = System.currentTimeMillis();
        int boxTimeout = ExplosionBoxGui.slider.currentValue * 1000;
        boxSet.removeIf(aabbTime -> time - aabbTime.getTime() > boxTimeout);
    }

    @SubscribeEvent
    public void onLastWorldEvent(RenderWorldLastEvent event) {
        if (boxSet.isEmpty()) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
        for (AxisAlignedBBTime box : boxSet) {
            RenderUtils.drawAABB(box, colorButton.getFinalRed(), colorButton.getFinalGreen(), colorButton.getFinalBlue(), colorButton.getFinalAlpha());
            RenderUtils.drawAABBOutline(box, 2, outlineColorButton.getFinalRed(), outlineColorButton.getFinalGreen(), outlineColorButton.getFinalBlue(), outlineColorButton.getFinalAlpha());
        } GlStateManager.popMatrix();
    }

    public static ExplosionBox getInstance() {
        return instance;
    }
}