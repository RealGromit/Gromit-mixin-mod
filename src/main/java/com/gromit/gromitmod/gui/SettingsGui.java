package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class SettingsGui extends MainGui {

    public SettingsGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        settings.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.normal.drawString("Open gui keybind:", mainGuiPointX + 68, mainGuiPointY + 41, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {settings.setState(false);}
}
