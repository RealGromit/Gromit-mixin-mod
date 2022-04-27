package com.gromit.gromitmod.gui.maingui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.AbstractGui;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class SettingsGui extends AbstractGui {

    public SettingsGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.normal.drawString("Open gui keybind:", mainGuiPointX + 68, mainGuiPointY + 41, Color.WHITE.getRGB());
    }
}
