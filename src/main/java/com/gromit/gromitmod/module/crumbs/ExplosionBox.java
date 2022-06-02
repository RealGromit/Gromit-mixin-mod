package com.gromit.gromitmod.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.event.network.InboundPacket;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.ColorUtils;
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

@Module(moduleName = "ExplosionBoxModule")
public class ExplosionBox extends AbstractModule {

    private static ExplosionBox instance;
    private static final GromitMod gromitMod = GromitMod.getInstance();
    private transient RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
    private transient Set<AxisAlignedBBTime> boxSet = ConcurrentHashMap.newKeySet();
    private transient Minecraft minecraft = Minecraft.getMinecraft();

    public final ColorButton boxColorButton = new ColorButton(gromitMod.getNewButtonId(), 4, 4, 0, 0, 60, 60);
    public final ColorButton outlineColorButton = new ColorButton(gromitMod.getNewButtonId(), 4, 4, 0, 0, 60, 60);
    public final Slider timeoutSlider = new Slider(gromitMod.getNewButtonId(), 95, 2, 1, 20, 100);
    public final CheckboxButton boxPrecision = new CheckboxButton(gromitMod.getNewButtonId(), 4, 4);
    public final CheckboxButton stateCheckbox = new CheckboxButton(gromitMod.getNewButtonId(), 4, 4,
            button -> register(),
            button -> unregister());

    public ExplosionBox() {
        instance = this;
    }

    @SubscribeEvent
    public void onPacketReceive(InboundPacket event) {
        if (!(event.getPacket() instanceof S27PacketExplosion)) return;
        S27PacketExplosion explosion = (S27PacketExplosion) event.getPacket();
        AxisAlignedBBTime box;
        if (boxPrecision.isState()) box = new AxisAlignedBBTime(explosion.getX() - 0.1, explosion.getY() + 0.4 - 0.06125, explosion.getZ() - 0.1, explosion.getX() + 0.1, explosion.getY() + 0.6 - 0.06125, explosion.getZ() + 0.1, System.currentTimeMillis());
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
        int boxTimeout = timeoutSlider.currentValue * 1000;
        boxSet.removeIf(aabbTime -> time - aabbTime.getTime() > boxTimeout);
    }

    @SubscribeEvent
    public void onLastWorldEvent(RenderWorldLastEvent event) {
        if (boxSet.isEmpty()) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
        for (AxisAlignedBBTime box : boxSet) {
            RenderUtils.drawAABB(box, boxColorButton.getRed(), boxColorButton.getGreen(), boxColorButton.getBlue(), boxColorButton.getAlpha());
            RenderUtils.drawAABBOutline(box, 2, outlineColorButton.getRed(), outlineColorButton.getGreen(), outlineColorButton.getBlue(), outlineColorButton.getAlpha());
        } GlStateManager.popMatrix();
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
        boxSet = ConcurrentHashMap.newKeySet();
        minecraft = Minecraft.getMinecraft();
        renderManager = Minecraft.getMinecraft().getRenderManager();
        stateCheckbox.updateLambda(button -> register(), button -> unregister());
        ColorUtils.colorButtons.add(boxColorButton);
        ColorUtils.colorButtons.add(outlineColorButton);
    }

    public static ExplosionBox getInstance() {
        return instance;
    }
}