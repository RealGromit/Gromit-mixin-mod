package com.gromit.gromitmod.module.crumbs;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.event.network.InboundPacket;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.saver.PersistCheckbox;
import com.gromit.gromitmod.saver.PersistColorButton;
import com.gromit.gromitmod.saver.PersistSlider;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Module(moduleName = "ExplosionBoxModule")
public class ExplosionBox extends AbstractModule implements Serializable {

    private static final long serialVersionUID = 3175898751531181260L;

    private static ExplosionBox instance;
    private transient RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();;
    private transient Set<AxisAlignedBBTime> boxSet = ConcurrentHashMap.newKeySet();
    private transient Minecraft minecraft = Minecraft.getMinecraft();;

    public final PersistCheckbox persistToggle = new PersistCheckbox();
    public final PersistColorButton persistBoxColor = new PersistColorButton();
    public final PersistCheckbox persistBoxColorCheckbox = new PersistCheckbox();
    public final PersistColorButton persistOutlineColor = new PersistColorButton();
    public final PersistCheckbox persistOutlineColorCheckbox = new PersistCheckbox();
    public final PersistSlider persistTimeoutSlider = new PersistSlider();
    public final PersistCheckbox persistBoxPrecision = new PersistCheckbox();

    // Constructor needs to be free of params due to Class.newInstance()
    public ExplosionBox() {
        instance = this;
    }

    @SubscribeEvent
    public void onPacketReceive(InboundPacket event) {
        if (!(event.getPacket() instanceof S27PacketExplosion)) return;
        S27PacketExplosion explosion = (S27PacketExplosion) event.getPacket();
        AxisAlignedBBTime box;
        if (persistBoxPrecision.isState()) box = new AxisAlignedBBTime(explosion.getX() - 0.1, explosion.getY() + 0.4 - 0.06125, explosion.getZ() - 0.1, explosion.getX() + 0.1, explosion.getY() + 0.6 - 0.06125, explosion.getZ() + 0.1, System.currentTimeMillis());
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
            RenderUtils.drawAABB(box, persistBoxColor.getRed(), persistBoxColor.getGreen(), persistBoxColor.getBlue(), persistBoxColor.getAlpha());
            RenderUtils.drawAABBOutline(box, 2, persistOutlineColor.getRed(), persistOutlineColor.getGreen(), persistOutlineColor.getBlue(), persistOutlineColor.getAlpha());
        } GlStateManager.popMatrix();
    }

    // Needed to reassign transient fields
    @Override
    public void updateAfterDeserialization() {
        instance = this;
        boxSet = ConcurrentHashMap.newKeySet();
        minecraft = Minecraft.getMinecraft();
        renderManager = Minecraft.getMinecraft().getRenderManager();
    }

    public static ExplosionBox getInstance() {
        return instance;
    }
}