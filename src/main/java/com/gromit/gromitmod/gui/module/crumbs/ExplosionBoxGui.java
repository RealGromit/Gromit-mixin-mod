package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import lombok.Getter;

import java.awt.Color;

public class ExplosionBoxGui extends CrumbsModuleGui {

    @Getter private static ExplosionBoxGui instance;
    private final ColorButton boxColorButton = ExplosionBox.getInstance().boxColorButton;
    private final ColorButton outlineColorButton = ExplosionBox.getInstance().outlineColorButton;
    private final Slider timeoutSlider = ExplosionBox.getInstance().timeoutSlider;
    private final ToggleButton boxPrecision = ExplosionBox.getInstance().boxPrecision;

    @Override
    public void initGui() {
        super.initGui();

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

        title.drawString("Explosion Box", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Box color", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Outline color", mainGuiPointX + 272, mainGuiPointY + 228, Color.WHITE.getRGB());
        normal.drawString("Precision", mainGuiPointX + 272, mainGuiPointY + 256, Color.WHITE.getRGB());
        normal.drawString("Box timeout in seconds:", mainGuiPointX + 272, mainGuiPointY + 300, Color.WHITE.getRGB());
        timeoutSlider.drawButton(mouseX, mouseY);
        boxPrecision.drawButton(mouseX, mouseY);
        normal.drawString(timeoutSlider.buttonName, mainGuiPointX + 488, mainGuiPointY + 300, Color.WHITE.getRGB());
        boxColorButton.drawButton(mouseX, mouseY);
        outlineColorButton.drawButton(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {explosionBox.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }
}
