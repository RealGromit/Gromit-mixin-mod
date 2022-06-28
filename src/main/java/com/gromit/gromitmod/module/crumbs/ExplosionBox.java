package com.gromit.gromitmod.module.crumbs;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.event.network.InboundPacketEvent;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.StateDisableListener;
import com.gromit.gromitmod.gui.button.listener.StateEnableListener;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.RenderUtils;
import lombok.Getter;
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

    @Getter private static ExplosionBox instance;
    private transient RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
    private transient Set<AxisAlignedBBTime> boxSet = ConcurrentHashMap.newKeySet();
    private transient Minecraft minecraft = Minecraft.getMinecraft();

    public final ColorButton boxColorButton = new ColorButton(MainGui.mainGuiPointX + 520, MainGui.mainGuiPointY + 200, MainGui.mainGuiPointX + MainGui.guiWidth + 25, MainGui.mainGuiPointY, 240, 240)
            .setWidth(16)
            .setHeight(16);

    public final ColorButton outlineColorButton = new ColorButton(MainGui.mainGuiPointX + 520, MainGui.mainGuiPointY + 228, MainGui.mainGuiPointX + MainGui.guiWidth + 25, MainGui.mainGuiPointY + 284, 240, 240)
            .setWidth(16)
            .setHeight(16);

    public final Slider timeoutSlider = new Slider(MainGui.mainGuiPointX + 272, MainGui.mainGuiPointY + 328, false)
            .setWidth(380)
            .setHeight(8)
            .setMinMax(1, 20);

    public final ToggleButton boxPrecision = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 258);

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 194, MainGui.mainGuiPointY + 158)
            .addButtonListener((StateEnableListener) button -> register())
            .addButtonListener((StateDisableListener) button -> unregister());

    public ExplosionBox() {
        instance = this;
    }

    @SubscribeEvent
    public void onPacketReceive(InboundPacketEvent event) {
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
        float boxTimeout = timeoutSlider.currentValue * 1000;
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
        stateCheckbox
                .addButtonListener((StateEnableListener) button -> register())
                .addButtonListener((StateDisableListener) button -> unregister());
    }
}