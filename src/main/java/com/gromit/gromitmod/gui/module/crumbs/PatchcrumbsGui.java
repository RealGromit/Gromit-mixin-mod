package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.crumbs.Patchcrumbs;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.Color;

public class PatchcrumbsGui extends CrumbsModuleGui {

    private static PatchcrumbsGui instance;
    private final ColorButton boxColorButton = Patchcrumbs.getInstance().boxColorButton;
    private final ColorButton outlineColorButton = Patchcrumbs.getInstance().outlineColorButton;
    private final ColorButton lineColorButton = Patchcrumbs.getInstance().lineColorButton;
    private final Slider timeoutSlider = Patchcrumbs.getInstance().timeoutSlider;

    public PatchcrumbsGui(GromitMod gromitMod) {
        super(gromitMod);
        instance = this;
    }

    @Override
    public void initGui() {
        super.initGui();

        boxColorButton.updateColorButton(mainGuiPointX + 130, mainGuiPointY + 48, mainGuiPointX + guiWidth + 5, mainGuiPointY, guiScale);
        boxColorButton.getChroma().updateButton(mainGuiPointX + guiWidth + 35, mainGuiPointY + 44, guiScale);
        outlineColorButton.updateColorButton(mainGuiPointX + 130, mainGuiPointY + 55, mainGuiPointX + guiWidth + 5, mainGuiPointY + 51, guiScale);
        outlineColorButton.getChroma().updateButton(mainGuiPointX + guiWidth + 35, mainGuiPointY + 40 + 55, guiScale);
        lineColorButton.updateColorButton(mainGuiPointX + 130, mainGuiPointY + 62, mainGuiPointX + guiWidth + 5, mainGuiPointY + 102, guiScale);
        lineColorButton.getChroma().updateButton(mainGuiPointX + guiWidth + 35, mainGuiPointY + 80 + 66, guiScale);
        timeoutSlider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 80, guiScale);

        buttonList.add(boxColorButton);
        buttonList.add(boxColorButton.getChroma());
        buttonList.add(outlineColorButton);
        buttonList.add(outlineColorButton.getChroma());
        buttonList.add(lineColorButton);
        buttonList.add(lineColorButton.getChroma());
        buttonList.add(timeoutSlider);
        patchcrumbs.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Patchcrumbs", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box color", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Outline color", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Line color", mainGuiPointX + 68, mainGuiPointY + 64, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box timeout in seconds:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());

        boxColorButton.drawButton(minecraft, mouseX, mouseY);
        outlineColorButton.drawButton(minecraft, mouseX, mouseY);
        lineColorButton.drawButton(minecraft, mouseX, mouseY);
        timeoutSlider.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(timeoutSlider.displayString, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {patchcrumbs.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static PatchcrumbsGui getInstance() {
        return instance;
    }
}
