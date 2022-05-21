package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.FunModuleGui;
import com.gromit.gromitmod.gui.module.RenderModuleGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MainGui extends GuiScreen {

    private static MainGui instance;
    protected final GromitMod gromitMod;
    protected static final Minecraft minecraft = GromitMod.getInstance().getMinecraft();
    protected final int guiWidth = 270, guiHeight = 150;
    protected int mainGuiPointX, mainGuiPointY;
    protected double guiScale;

    private final ResourceLocation bounds = new ResourceLocation("astrix", "border.png");
    private final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    protected static final TextButton render = new TextButton(0, 4, "Render",
            (button) -> {
                if (Saver.getRenderModuleGui() != null) minecraft.displayGuiScreen(Saver.getRenderModuleGui());
                else minecraft.displayGuiScreen(RenderModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton fps = new TextButton(1, 4, "Fps",
            (button) -> {
                if (Saver.getFpsModuleGui() != null) minecraft.displayGuiScreen(Saver.getFpsModuleGui());
                else minecraft.displayGuiScreen(FpsModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton crumbs = new TextButton(2, 4, "Crumbs",
            (button) -> {
                if (Saver.getCrumbsModuleGui() != null) minecraft.displayGuiScreen(Saver.getCrumbsModuleGui());
                else minecraft.displayGuiScreen(CrumbsModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton fun = new TextButton(3, 4, "Fun",
            (button) -> {
                if (Saver.getFunModuleGui() != null) minecraft.displayGuiScreen(Saver.getFunModuleGui());
                else minecraft.displayGuiScreen(FunModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton settings = new TextButton(4, 4, "Settings",
            (button) -> minecraft.displayGuiScreen(SettingsGui.getInstance()),
            (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    public MainGui(GromitMod gromitMod) {
        setInstance();
        this.gromitMod = gromitMod;
    }

    @Override
    public void initGui() {
        guiScale = width / 480.0;
        mainGuiPointX = (int) ((width / guiScale - guiWidth) / 2);
        mainGuiPointY = (int) ((height / guiScale - guiHeight) / 2);
        buttonList.clear();
        render.updateButton(mainGuiPointX + 63, mainGuiPointY + 16, guiScale);
        fps.updateButton(mainGuiPointX + 63 + 16 + 25, mainGuiPointY + 16, guiScale);
        crumbs.updateButton(mainGuiPointX + 63 + 16 + 8  + 50, mainGuiPointY + 16, guiScale);
        fun.updateButton(mainGuiPointX + 63 + 16 + 8 + 18  + 75, mainGuiPointY + 16, guiScale);
        settings.updateButton(mainGuiPointX + 63 + 16 + 8 + 18 + 8  + 100, mainGuiPointY + 16, guiScale);
        buttonList.add(render);
        buttonList.add(fps);
        buttonList.add(crumbs);
        buttonList.add(fun);
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
        render.drawButton(minecraft, mouseX, mouseY);
        fps.drawButton(minecraft, mouseX, mouseY);
        crumbs.drawButton(minecraft, mouseX, mouseY);
        fun.drawButton(minecraft, mouseX, mouseY);
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
    protected void setInstance() {instance = this;}

    public static MainGui getInstance() {
        return instance;
    }
}
