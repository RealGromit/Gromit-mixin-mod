package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public abstract class ScalableGui extends GuiScreen {

    protected static final Minecraft minecraft = Minecraft.getMinecraft();
    protected static final GromitMod gromitMod = GromitMod.getInstance();
    protected double guiScale;

    @Override
    public void initGui() {guiScale = width / 480.0;}

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {GlStateManager.scale(guiScale, guiScale, guiScale);}

    @Override
    public boolean doesGuiPauseGame() {return false;}
}
