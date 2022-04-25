package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MainGui extends GuiScreen {

    private final GromitMod gromitMod;
    private final Minecraft minecraft;
    private final int guiWidth = 270, guiHeight = 150;
    private int mainGuiPointX, mainGuiPointY;
    private double guiScale;

    private final ResourceLocation bounds = new ResourceLocation("astrix", "border.png");
    private final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    public MainGui(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
    }

    @Override
    public void initGui() {
        guiScale = width / 480.0;
        mainGuiPointX = (int) ((width / guiScale - guiWidth) / 2);
        mainGuiPointY = (int) ((height / guiScale - guiHeight) / 2);
        buttonList.clear();
        buttonList.add(new TextButton(0, mainGuiPointX + 77, mainGuiPointY + 16, (int) ((int) FontUtil.normal.getStringWidth("Main Menu") / 1.9), 4, "Main Menu", guiScale, () -> minecraft.thePlayer.sendChatMessage("Triggered")));
        buttonList.add(new TextButton(1, mainGuiPointX + 137, mainGuiPointY + 16, (int) ((int) FontUtil.normal.getStringWidth("Modules") / 1.9), 4, "Modules", guiScale, () -> minecraft.thePlayer.sendChatMessage("cool!!!")));
        buttonList.add(new TextButton(2, mainGuiPointX + 190, mainGuiPointY + 16, (int) ((int) FontUtil.normal.getStringWidth("Settings") / 1.9), 4, "Settings", guiScale, () -> minecraft.thePlayer.sendChatMessage("settings!!!")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        GlStateManager.scale(guiScale, guiScale, guiScale);
        minecraft.getTextureManager().bindTexture(bounds);
        RenderUtils.drawTextureColor(mainGuiPointX - 1, mainGuiPointY - 1, guiWidth + 2, guiHeight + 2, 0, 0, 1, 1, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawRectangle(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 54, 54, 54, 225);
        RenderUtils.drawLine(mainGuiPointX + 45, mainGuiPointY + 23, 205, 0, 3, 255, 255, 255, 255);
        minecraft.getTextureManager().bindTexture(gromit);
        RenderUtils.drawCircle(mainGuiPointX + 21, mainGuiPointY + 18, 11, 3, 200, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawTexture(mainGuiPointX + 5, mainGuiPointY + 5, 33, 25, 0, 0, 1, 1);
        for (GuiButton textButton : buttonList) {
            textButton.drawButton(minecraft, mouseX, mouseY);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
