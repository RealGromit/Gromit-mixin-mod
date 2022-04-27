package com.gromit.gromitmod.gui.subgui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.AbstractModuleGui;
import com.gromit.gromitmod.gui.button.ChangeableButton;
import com.gromit.gromitmod.gui.button.ScrollableButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class RangeCalcModuleGui extends AbstractModuleGui {

    public final ChangeableButton changeableButton = new ChangeableButton(3, mainGuiPointX + 82, mainGuiPointY + 53, (int) FontUtil.normal.getStringWidth("disabled") / 2, 4, "disabled", "enabled", guiScale, () -> {}, () -> {});
    public final Slider rangeCalcSlider = new Slider(mainGuiPointX + 74, mainGuiPointY + 95, 79, 2, 1, 1, 80, 100);

    public RangeCalcModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        updateTextButton(changeableButton, mainGuiPointX + 82, mainGuiPointY + 53);
        updateSlider(rangeCalcSlider, mainGuiPointX + 74, mainGuiPointY + 95);
        buttonList.add(changeableButton);
        sliderList.add(rangeCalcSlider);
        rangeCalc.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Cannon range calculator", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Calculate range by right clicking a dispenser with gold ingot", mainGuiPointX + 68, mainGuiPointY + 46, Color.WHITE.getRGB());
        FontUtil.normal.drawString("State:", mainGuiPointX + 68, mainGuiPointY + 55, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Amount of entity flight in ticks:", mainGuiPointX + 68, mainGuiPointY + 86, Color.WHITE.getRGB());
        for (GuiButton textButton : buttonList) {
            if (textButton instanceof ScrollableButton) continue;
            textButton.drawButton(minecraft, mouseX, mouseY);
        }
        for (Slider slider : sliderList) {
            slider.drawSlider(mouseX, mouseY);
        }
    }

    @Override
    public void onGuiClosed() {
        rangeCalc.setState(false);
    }
}
