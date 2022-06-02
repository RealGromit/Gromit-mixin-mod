package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.module.RenderModuleGui;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.util.ResourceLocation;

public class MainGui extends ScalableGui {

    private static MainGui instance;
    protected final GromitMod gromitMod;
    protected final int guiWidth = 270, guiHeight = 150;
    protected int mainGuiPointX = (480 - guiWidth) / 2;
    protected int mainGuiPointY = (270 - guiHeight) / 2;

    private final ResourceLocation bounds = new ResourceLocation("astrix", "border.png");
    private final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    protected static final TextButton render = new TextButton(GromitMod.getInstance().getNewButtonId(), 4, "Mods",
            (button) -> {
                if (GlobalSaver.getRenderModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getRenderModuleGui());
                else minecraft.displayGuiScreen(RenderModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton fps = new TextButton(GromitMod.getInstance().getNewButtonId(), 4, "Fps",
            (button) -> {
                if (GlobalSaver.getFpsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getFpsModuleGui());
                else minecraft.displayGuiScreen(FpsModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton crumbs = new TextButton(GromitMod.getInstance().getNewButtonId(), 4, "Crumbs",
            (button) -> {
                if (GlobalSaver.getCrumbsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getCrumbsModuleGui());
                else minecraft.displayGuiScreen(CrumbsModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton fun = new TextButton(GromitMod.getInstance().getNewButtonId(), 4, "Other",
            (button) -> {
                if (GlobalSaver.getOtherModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getOtherModuleGui());
                else minecraft.displayGuiScreen(OtherModuleGui.getInstance());
            }, (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton settings = new TextButton(GromitMod.getInstance().getNewButtonId(), 4, "Settings",
            (button) -> minecraft.displayGuiScreen(SettingsGui.getInstance()),
            (button) -> minecraft.displayGuiScreen(MainGui.getInstance()));

    public MainGui(GromitMod gromitMod) {
        setInstance();
        this.gromitMod = gromitMod;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        render.updateButton(mainGuiPointX + 63, mainGuiPointY + 16, guiScale);
        fps.updateButton(mainGuiPointX + 63 + 16 + 25, mainGuiPointY + 16, guiScale);
        crumbs.updateButton(mainGuiPointX + 63 + 16 + 8  + 50, mainGuiPointY + 16, guiScale);
        fun.updateButton(mainGuiPointX + 63 + 16 + 8 + 18  + 75, mainGuiPointY + 16, guiScale);
        settings.updateButton(mainGuiPointX + 63 + 16 + 8 + 21 + 8  + 100, mainGuiPointY + 16, guiScale);
        buttonList.add(render);
        buttonList.add(fps);
        buttonList.add(crumbs);
        buttonList.add(fun);
        buttonList.add(settings);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        minecraft.getTextureManager().bindTexture(bounds);
        RenderUtils.drawTextureColor(mainGuiPointX - 1, mainGuiPointY - 1, guiWidth + 2, guiHeight + 2, 0, 0, 1, 1, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawRectangle(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 54, 54, 54, 225);
        RenderUtils.drawLine(mainGuiPointX + 45, mainGuiPointY + 23, 205, 0, 3, false, 255, 255, 255, 255);
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
            GlobalSaver.setLastScreen(this);
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
