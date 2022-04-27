package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ScrollableButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractGui extends GuiScreen {

    protected final GromitMod gromitMod;
    protected final Minecraft minecraft;
    protected final int guiWidth = 270, guiHeight = 150;
    protected int mainGuiPointX, mainGuiPointY;
    protected double guiScale;

    private final ResourceLocation bounds = new ResourceLocation("astrix", "border.png");
    private final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    private final TextButton modules;
    private final TextButton settings;

    public AbstractGui(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
        modules = new TextButton(0, mainGuiPointX + 90, mainGuiPointY + 16, (int) (FontUtil.normal.getStringWidth("Modules") / 1.9), 4, "Modules", guiScale, () ->
            minecraft.displayGuiScreen(gromitMod.getGuiManager().getModuleGui()));
        settings = new TextButton(1, mainGuiPointX + 170, mainGuiPointY + 16, (int) ((int) FontUtil.normal.getStringWidth("Settings") / 1.9), 4, "Settings", guiScale, () ->
            minecraft.displayGuiScreen(gromitMod.getGuiManager().getSettingsGui()));
    }

    @Override
    public void initGui() {
        guiScale = width / 480.0;
        mainGuiPointX = (int) ((width / guiScale - guiWidth) / 2);
        mainGuiPointY = (int) ((height / guiScale - guiHeight) / 2);
        buttonList.clear();
        updateTextButton(modules, mainGuiPointX + 90, mainGuiPointY + 16);
        updateTextButton(settings, mainGuiPointX + 170, mainGuiPointY + 16);
        buttonList.add(modules);
        buttonList.add(settings);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.scale(guiScale, guiScale, guiScale);
        minecraft.getTextureManager().bindTexture(bounds);
        RenderUtils.drawTextureColor(mainGuiPointX - 1, mainGuiPointY - 1, guiWidth + 2, guiHeight + 2, 0, 0, 1, 1, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawRectangle(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 54, 54, 54, 225);
        RenderUtils.drawLine(mainGuiPointX + 45, mainGuiPointY + 23, 205, 0, 3, 255, 255, 255, 255);
        minecraft.getTextureManager().bindTexture(gromit);
        RenderUtils.drawCircle(mainGuiPointX + 21, mainGuiPointY + 18, 11, 3, 200, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawTexture(mainGuiPointX + 5, mainGuiPointY + 5, 33, 25, 0, 0, 1, 1);
        for (GuiButton button : buttonList) {
            if (button instanceof ScrollableButton) continue;
            button.drawButton(minecraft, mouseX, mouseY);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    protected <T extends TextButton> void updateTextButton(T button, int x1, int y1) {
        button.setGuiScale(guiScale);
        button.xPosition = x1;
        button.yPosition = y1;
    }
}
