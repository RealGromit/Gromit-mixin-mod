package com.gromit.gromitmod.gui.subgui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.AbstractModuleGui;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class RangeCalcModuleGui extends AbstractModuleGui {

    public RangeCalcModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        rangeCalc.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.normal.drawString("Cannon range calculator", mainGuiPointX + 68, mainGuiPointY + 41, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Calculate range by right clicking a dispenser with gold ingot", mainGuiPointX + 68, mainGuiPointY + 46, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Amount of entity flight in ticks:", mainGuiPointX + 68, mainGuiPointY + 70, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {
        rangeCalc.setState(false);
    }
}
