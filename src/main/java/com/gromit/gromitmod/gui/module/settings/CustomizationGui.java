package com.gromit.gromitmod.gui.module.settings;

import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.module.SettingsGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.settings.Customizations;
import lombok.Getter;

import java.awt.*;

public class CustomizationGui extends SettingsGui {

    @Getter private static CustomizationGui instance;

    private final ToggleButton circleRgb = Customizations.getInstance().circleRgb;
    private final ToggleButton outlineRgb = Customizations.getInstance().outlineRgb;
    private final Slider circleRadius = Customizations.getInstance().circleRadius;
    private final Slider circleLineWidth = Customizations.getInstance().circleLineWidth;
    private final Slider circleFade = Customizations.getInstance().circleFade;
    private final Slider circleSpeed = Customizations.getInstance().circleSpeed;
    private final Slider outlineRadius = Customizations.getInstance().outlineRadius;
    private final Slider outlineLineWidth = Customizations.getInstance().outlineLineWidth;
    private final Slider outlineFade = Customizations.getInstance().outlineFade;
    private final Slider outlineSpeed = Customizations.getInstance().outlineSpeed;
    private final ColorButton circleColor = Customizations.getInstance().circleColor;
    private final ColorButton outlineColor = Customizations.getInstance().outlineColor;

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(circleRgb);
        buttonList.add(outlineRgb);
        buttonList.add(circleRadius);
        buttonList.add(circleLineWidth);
        buttonList.add(circleFade);
        buttonList.add(circleSpeed);
        buttonList.add(outlineRadius);
        buttonList.add(outlineLineWidth);
        buttonList.add(outlineFade);
        buttonList.add(outlineSpeed);
        buttonList.add(circleColor);
        buttonList.add(circleColor.getChroma());
        buttonList.add(outlineColor);
        buttonList.add(outlineColor.getChroma());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        title.drawString("Customizations", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Circle rgb", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Circle Radius:", mainGuiPointX + 272, mainGuiPointY + 228, Color.WHITE.getRGB());
        normal.drawString("Circle Line Width:", mainGuiPointX + 272, mainGuiPointY + 256, Color.WHITE.getRGB());
        normal.drawString("Circle Fade:", mainGuiPointX + 272, mainGuiPointY + 284, Color.WHITE.getRGB());
        normal.drawString("Circle speed:", mainGuiPointX + 272, mainGuiPointY + 312, Color.WHITE.getRGB());
        normal.drawString("Circle Color", mainGuiPointX + 272, mainGuiPointY + 340, Color.WHITE.getRGB());

        normal.drawString("Outline rgb", mainGuiPointX + 272, mainGuiPointY + 378, Color.WHITE.getRGB());
        normal.drawString("Outline Radius:", mainGuiPointX + 272, mainGuiPointY + 406, Color.WHITE.getRGB());
        normal.drawString("Outline Line Width:", mainGuiPointX + 272, mainGuiPointY + 434, Color.WHITE.getRGB());
        normal.drawString("Outline Fade:", mainGuiPointX + 272, mainGuiPointY + 462, Color.WHITE.getRGB());
        normal.drawString("Outline Speed:", mainGuiPointX + 272, mainGuiPointY + 490, Color.WHITE.getRGB());
        normal.drawString("Outline Color", mainGuiPointX + 272, mainGuiPointY + 518, Color.WHITE.getRGB());

        circleRgb.drawButton(mouseX, mouseY);
        circleRadius.drawButton(mouseX, mouseY);
        normal.drawString(circleRadius.buttonName, mainGuiPointX + 396, mainGuiPointY + 228, Color.WHITE.getRGB());
        circleLineWidth.drawButton(mouseX, mouseY);
        normal.drawString(circleLineWidth.buttonName, mainGuiPointX + 433, mainGuiPointY + 256, Color.WHITE.getRGB());
        circleFade.drawButton(mouseX, mouseY);
        normal.drawString(circleFade.buttonName, mainGuiPointX + 380, mainGuiPointY + 284, Color.WHITE.getRGB());
        circleSpeed.drawButton(mouseX, mouseY);
        normal.drawString(circleSpeed.buttonName, mainGuiPointX + 393, mainGuiPointY + 312, Color.WHITE.getRGB());
        circleColor.drawButton(mouseX, mouseY);

        outlineRgb.drawButton(mouseX, mouseY);
        outlineRadius.drawButton(mouseX, mouseY);
        normal.drawString(outlineRadius.buttonName, mainGuiPointX + 411, mainGuiPointY + 406, Color.WHITE.getRGB());
        outlineLineWidth.drawButton(mouseX, mouseY);
        normal.drawString(outlineLineWidth.buttonName, mainGuiPointX + 447, mainGuiPointY + 434, Color.WHITE.getRGB());
        outlineFade.drawButton(mouseX, mouseY);
        normal.drawString(outlineFade.buttonName, mainGuiPointX + 394, mainGuiPointY + 462, Color.WHITE.getRGB());
        outlineSpeed.drawButton(mouseX, mouseY);
        normal.drawString(outlineSpeed.buttonName, mainGuiPointX + 408, mainGuiPointY + 490, Color.WHITE.getRGB());
        outlineColor.drawButton(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        customization.setState(false);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
