package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.crumbs.Patchcrumbs;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import lombok.Getter;

import java.awt.Color;

public class PatchcrumbsGui extends CrumbsModuleGui {

    @Getter private static PatchcrumbsGui instance;
    private final ColorButton boxColorButton = Patchcrumbs.getInstance().boxColorButton;
    private final ColorButton outlineColorButton = Patchcrumbs.getInstance().outlineColorButton;
    private final ColorButton lineColorButton = Patchcrumbs.getInstance().lineColorButton;
    private final Slider timeoutSlider = Patchcrumbs.getInstance().timeoutSlider;

    public PatchcrumbsGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();

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

        FontManager.getTitleSize().drawString("Patchcrumbs", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Box color", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Outline color", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Line color", mainGuiPointX + 68, mainGuiPointY + 64, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Box timeout in seconds:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());

        boxColorButton.drawButton(mouseX, mouseY);
        outlineColorButton.drawButton(mouseX, mouseY);
        lineColorButton.drawButton(mouseX, mouseY);
        timeoutSlider.drawButton(mouseX, mouseY);
        FontManager.getNormalSize().drawString(timeoutSlider.buttonName, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {patchcrumbs.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }
}
