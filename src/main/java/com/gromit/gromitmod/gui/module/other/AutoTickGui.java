package com.gromit.gromitmod.gui.module.other;

import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.other.AutoTick;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;

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

        FontManager.getTitleSize().drawString("Auto Tick Repeaters", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Automatically Tick Repeaters When You Rightclick Them", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Ticks On Repeater:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());
        tickSlider.drawButton(mouseX, mouseY);
        FontManager.getNormalSize().drawString(tickSlider.buttonName, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
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
