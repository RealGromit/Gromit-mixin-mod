package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.listener.ClickDisableListener;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.module.*;
import com.gromit.gromitmod.module.settings.Customizations;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class MainGui extends AbstractGui {

    @Getter private static MainGui instance;
    public static final int guiWidth = 1080, guiHeight = 600;
    public static final int mainGuiPointX = 420, mainGuiPointY = 240;
    private final Customizations customizations = Customizations.getInstance();

    private static final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    protected static final TextButton mods = new TextButton(normal, mainGuiPointX + 272, mainGuiPointY + 64)
            .setButtonText("Mods")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getModsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getModsModuleGui());
                else minecraft.displayGuiScreen(ModsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton fps = new TextButton(normal, mainGuiPointX + 364 + normal.getWidth("Mods"), mainGuiPointY + 64)
            .setButtonText("Fps")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getFpsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getFpsModuleGui());
                else minecraft.displayGuiScreen(FpsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton crumbs = new TextButton(normal, mainGuiPointX + 456 + normal.getWidth("Mods") + normal.getWidth("Fps"), mainGuiPointY + 64)
            .setButtonText("Crumbs")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getCrumbsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getCrumbsModuleGui());
                else minecraft.displayGuiScreen(CrumbsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton other = new TextButton(normal, mainGuiPointX + 548 + normal.getWidth("Mods") + normal.getWidth("Fps") + normal.getWidth("Crumbs"), mainGuiPointY + 64)
            .setButtonText("Other")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getOtherModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getOtherModuleGui());
                else minecraft.displayGuiScreen(OtherModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton settings = new TextButton(normal, mainGuiPointX + 640 + normal.getWidth("Mods") + normal.getWidth("Fps") + normal.getWidth("Crumbs") + normal.getWidth("Other"), mainGuiPointY + 64)
            .setButtonText("Settings")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getSettingsGui() != null) minecraft.displayGuiScreen(GlobalSaver.getSettingsGui());
                else minecraft.displayGuiScreen(SettingsGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    public MainGui() {
        setInstance();
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(mods);
        buttonList.add(fps);
        buttonList.add(crumbs);
        buttonList.add(other);
        buttonList.add(settings);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (customizations.isState()) {
            RenderUtils.drawRoundedRectangle(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 1, customizations.outlineRadius.currentValue, ColorUtils.RGBA2Integer(54, 54, 54, 225));
            RenderUtils.drawRoundedRectangleOutline(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, customizations.outlineRadius.currentValue, customizations.outlineLineWidth.currentValue, customizations.outlineFade.currentValue, customizations.outlineColor.getColor(), customizations.outlineRgb.isState());
            RenderUtils.drawCircleOutline(mainGuiPointX + 43 + 48, mainGuiPointY + 28 + 48, customizations.circleRadius.currentValue, customizations.circleLineWidth.currentValue, customizations.circleFade.currentValue, customizations.circleColor.getColor(), customizations.circleRgb.isState());
        } else {
            RenderUtils.drawRoundedRectangle(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 1, 30, ColorUtils.RGBA2Integer(54, 54, 54, 225));
            RenderUtils.drawRoundedRectangleOutline(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 30, 6, 5, Color.WHITE.getRGB(), true);
            RenderUtils.drawCircleOutline(mainGuiPointX + 43 + 48, mainGuiPointY + 28 + 48, 48, 4, 3, Color.WHITE.getRGB(), true);
        }
        minecraft.getTextureManager().bindTexture(gromit);
        RenderUtils.drawTexture(mainGuiPointX + 17, mainGuiPointY + 23, 150, 100, 0, 0, 1, 1);
        RenderUtils.drawLine(mainGuiPointX + 180, mainGuiPointY + 92, 820, 0, 3, false, 255, 255, 255, 255);
        mods.drawButton(mouseX, mouseY);
        fps.drawButton(mouseX, mouseY);
        crumbs.drawButton(mouseX, mouseY);
        other.drawButton(mouseX, mouseY);
        settings.drawButton(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    protected void setInstance() {instance = this;}
}
