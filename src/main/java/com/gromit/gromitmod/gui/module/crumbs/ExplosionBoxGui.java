package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class ExplosionBoxGui extends CrumbsModuleGui {

    private static ExplosionBoxGui instance;
    public static final ColorButton colorButton = new ColorButton(7, 0, 0, 4, 4, 0, 0, 60, 60);
    public static final Slider slider = new Slider(10, 0, 0, 95, 2, "", 1, 20, 100);
    public static final CheckboxButton precision = new CheckboxButton(8, 0, 0, 4, 4,
            (button) -> {},
            (button) -> {});

    public ExplosionBoxGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        colorButton.updateColorButton(mainGuiPointX + 130, mainGuiPointY + 48, mainGuiPointX + guiWidth + 5, mainGuiPointY, guiScale);
        precision.updateButton(mainGuiPointX + 130, mainGuiPointY + 55, guiScale);
        slider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 80, guiScale);
        buttonList.add(colorButton);
        buttonList.add(slider);
        buttonList.add(precision);
        explosionBox.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Explosion Box", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box color", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Precision", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box timeout in seconds:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());
        slider.drawButton(minecraft, mouseX, mouseY);
        precision.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(slider.displayString, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
        colorButton.drawButton(minecraft, mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        explosionBox.setState(false);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static ExplosionBoxGui getInstance() {
        return instance;
    }
}
