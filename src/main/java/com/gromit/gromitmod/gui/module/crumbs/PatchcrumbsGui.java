package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.crumbs.Patchcrumbs;
import lombok.Getter;

import java.awt.Color;

public class PatchcrumbsGui extends CrumbsModuleGui {

    @Getter private static PatchcrumbsGui instance;
    private final ColorButton boxColorButton = Patchcrumbs.getInstance().boxColorButton;
    private final ColorButton outlineColorButton = Patchcrumbs.getInstance().outlineColorButton;
    private final ColorButton lineColorButton = Patchcrumbs.getInstance().lineColorButton;
    private final Slider timeoutSlider = Patchcrumbs.getInstance().timeoutSlider;

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

        title.drawString("Patchcrumbs", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Box color", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Outline color", mainGuiPointX + 272, mainGuiPointY + 228, Color.WHITE.getRGB());
        normal.drawString("Line color", mainGuiPointX + 272, mainGuiPointY + 256, Color.WHITE.getRGB());
        normal.drawString("Box timeout in seconds:", mainGuiPointX + 272, mainGuiPointY + 300, Color.WHITE.getRGB());

        boxColorButton.drawButton(mouseX, mouseY);
        outlineColorButton.drawButton(mouseX, mouseY);
        lineColorButton.drawButton(mouseX, mouseY);
        timeoutSlider.drawButton(mouseX, mouseY);
        normal.drawString(timeoutSlider.buttonName, mainGuiPointX + 488, mainGuiPointY + 300, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {patchcrumbs.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }
}
