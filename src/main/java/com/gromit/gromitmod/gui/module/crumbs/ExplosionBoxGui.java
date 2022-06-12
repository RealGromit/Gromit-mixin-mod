package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import lombok.Getter;

import java.awt.Color;

public class ExplosionBoxGui extends CrumbsModuleGui {

    @Getter private static ExplosionBoxGui instance;
    private final ColorButton boxColorButton = ExplosionBox.getInstance().boxColorButton;
    private final ColorButton outlineColorButton = ExplosionBox.getInstance().outlineColorButton;
    private final Slider timeoutSlider = ExplosionBox.getInstance().timeoutSlider;
    private final ToggleButton boxPrecision = ExplosionBox.getInstance().boxPrecision;

    public ExplosionBoxGui() {
        super();
    }

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

        FontManager.getTitleSize().drawString("Explosion Box", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Box color", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Outline color", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Precision", mainGuiPointX + 68, mainGuiPointY + 64, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Box timeout in seconds:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());
        timeoutSlider.drawButton(mouseX, mouseY);
        boxPrecision.drawButton(mouseX, mouseY);
        FontManager.getNormalSize().drawString(timeoutSlider.buttonName, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
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
