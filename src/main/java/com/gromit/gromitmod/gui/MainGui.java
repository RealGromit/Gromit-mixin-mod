package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.handler.ButtonHandler;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.handler.Saver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MainGui extends GuiScreen {

    protected final GromitMod gromitMod;
    protected final Minecraft minecraft;
    protected final int guiWidth = 270, guiHeight = 150;
    protected int mainGuiPointX, mainGuiPointY;
    protected double guiScale;

    private final ResourceLocation bounds = new ResourceLocation("astrix", "border.png");
    private final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    protected final TextButton modules;
    protected final TextButton settings;

    public MainGui(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
        modules = ButtonHandler.getModules();
        settings = ButtonHandler.getSettings();
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
        RenderUtils.drawCircleOutline(mainGuiPointX + 21, mainGuiPointY + 18, 11, 3, 200, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawTexture(mainGuiPointX + 5, mainGuiPointY + 5, 33, 25, 0, 0, 1, 1);
        modules.drawButton(minecraft, mouseX, mouseY);
        settings.drawButton(minecraft, mouseX, mouseY);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            Saver.setLastScreen(this);
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }

    protected <T extends TextButton> void updateTextButton(T button, int x1, int y1) {
        button.setGuiScale(guiScale);
        button.xPosition = x1;
        button.yPosition = y1;
    }

    protected void updateSlider(Slider slider, int x1, int y1) {
        slider.guiScale = guiScale;
        slider.xPosition = x1;
        slider.yPosition = y1;
    }
}
