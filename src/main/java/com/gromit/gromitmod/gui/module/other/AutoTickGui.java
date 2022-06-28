package com.gromit.gromitmod.gui.module.other;

import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.other.AutoTick;

import java.awt.Color;

public class AutoTickGui extends OtherModuleGui {

    private static AutoTickGui instance;
    private final Slider tickSlider = AutoTick.getInstance().tickSlider;

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(tickSlider);
        autoTick.setState(true);
        debug.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        title.drawString("Auto Tick Repeaters", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Automatically Tick Repeaters When You Rightclick Them", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Ticks On Repeater:", mainGuiPointX + 272, mainGuiPointY + 300, Color.WHITE.getRGB());
        tickSlider.drawButton(mouseX, mouseY);
        normal.drawString(tickSlider.buttonName, mainGuiPointX + 488, mainGuiPointY + 300, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {
        autoTick.setState(false);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static AutoTickGui getInstance() {
        return instance;
    }
}
