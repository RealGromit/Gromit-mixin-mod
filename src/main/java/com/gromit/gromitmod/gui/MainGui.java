package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class MainGui extends GuiScreen {

    private final GromitMod gromitMod;
    private final Minecraft minecraft;
    private int guiWidth = 270, guiHeight = 150;
    private int mainGuiPointX, mainGuiPointY;
    private int guiScale;

    private final ResourceLocation bounds = new ResourceLocation("astrix", "border.png");
    private final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    public MainGui(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
    }

    @Override
    public void initGui() {
        guiScale = width / 480;
        guiWidth *= guiScale;
        guiHeight *= guiScale;
        mainGuiPointX = (width - guiWidth) / 2;
        mainGuiPointY = (height - guiHeight) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        minecraft.getTextureManager().bindTexture(bounds);
        RenderUtils.drawTextureColor(mainGuiPointX - guiScale, mainGuiPointY - guiScale, guiWidth + (2 * guiScale), guiHeight + (2 * guiScale), 0, 0, 1, 1, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawRectangle(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 54, 54, 54, 225);
        minecraft.getTextureManager().bindTexture(gromit);
        RenderUtils.drawCircle(mainGuiPointX + 21 * guiScale, mainGuiPointY + 18 * guiScale, 11 * guiScale, 3, 200, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawTexture(mainGuiPointX + 5 * guiScale, mainGuiPointY + 5 * guiScale, 33 * guiScale, 25 * guiScale, 0, 0, 1, 1);
    }

    @Override
    public void onGuiClosed() {
        guiWidth = 270;
        guiHeight = 150;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
