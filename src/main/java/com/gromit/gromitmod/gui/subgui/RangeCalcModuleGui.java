package com.gromit.gromitmod.gui.subgui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.ModuleGui;
import com.gromit.gromitmod.gui.button.ChangeableButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.handler.ButtonHandler;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class RangeCalcModuleGui extends ModuleGui {

    private final ChangeableButton stateButton;
    private final Slider slider;

    public RangeCalcModuleGui(GromitMod gromitMod) {
        super(gromitMod);
        stateButton = ButtonHandler.getStateButton();
        slider = ButtonHandler.getSlider();
    }

    @Override
    public void initGui() {
        super.initGui();

        updateTextButton(stateButton, mainGuiPointX + 82, mainGuiPointY + 53);
        updateSlider(slider, mainGuiPointX + 69, mainGuiPointY + 77);
        buttonList.add(stateButton);
        buttonList.add(slider);
        rangeCalc.setState(true);
        Saver.setLastModuleScreen(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Cannon range calculator", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Calculate range by right clicking a power dispenser with gold ingot", mainGuiPointX + 68, mainGuiPointY + 46, Color.WHITE.getRGB());
        FontUtil.normal.drawString("State:", mainGuiPointX + 68, mainGuiPointY + 55, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Amount of entity flight in ticks:", mainGuiPointX + 68, mainGuiPointY + 70, Color.WHITE.getRGB());
        stateButton.drawButton(minecraft, mouseX, mouseY);
        slider.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(slider.displayString, mainGuiPointX + 140, mainGuiPointY + 70, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {
        rangeCalc.setState(false);
    }
}
