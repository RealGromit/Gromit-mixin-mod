package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.Color;

public class ExplosionBoxGui extends CrumbsModuleGui {

    private static ExplosionBoxGui instance;
    private final ColorButton boxColorButton = ExplosionBox.getInstance().boxColorButton;
    private final ColorButton outlineColorButton = ExplosionBox.getInstance().outlineColorButton;
    private final Slider timeoutSlider = ExplosionBox.getInstance().timeoutSlider;
    private final CheckboxButton boxPrecision = ExplosionBox.getInstance().boxPrecision;

    public ExplosionBoxGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        boxColorButton.updateColorButton(mainGuiPointX + 130, mainGuiPointY + 48, mainGuiPointX + guiWidth + 5, mainGuiPointY, guiScale);
        boxColorButton.getChroma().updateButton(mainGuiPointX + guiWidth + 35, mainGuiPointY + 64, guiScale);
        outlineColorButton.updateColorButton(mainGuiPointX + 130, mainGuiPointY + 55, mainGuiPointX + guiWidth + 5, mainGuiPointY + 71, guiScale);
        outlineColorButton.getChroma().updateButton(mainGuiPointX + guiWidth + 35, mainGuiPointY + 60 + 75, guiScale);
        boxPrecision.updateButton(mainGuiPointX + 130, mainGuiPointY + 62, guiScale);
        timeoutSlider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 80, guiScale);
        buttonList.add(boxColorButton);
        buttonList.add(timeoutSlider);
        buttonList.add(boxPrecision);
        buttonList.add(boxColorButton.getChroma());
        buttonList.add(outlineColorButton);
        buttonList.add(outlineColorButton.getChroma());
        explosionBox.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Explosion Box", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box color", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Outline color", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Precision", mainGuiPointX + 68, mainGuiPointY + 64, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box timeout in seconds:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());
        timeoutSlider.drawButton(minecraft, mouseX, mouseY);
        boxPrecision.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(timeoutSlider.displayString, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
        boxColorButton.drawButton(minecraft, mouseX, mouseY);
        outlineColorButton.drawButton(minecraft, mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {explosionBox.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static ExplosionBoxGui getInstance() {
        return instance;
    }
}
