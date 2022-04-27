package com.gromit.gromitmod.gui.maingui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.AbstractGui;
import net.minecraft.client.gui.GuiButton;

public class MainGui extends AbstractGui {

    public MainGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (GuiButton textButton : buttonList) {
            textButton.drawButton(minecraft, mouseX, mouseY);
        }
    }
}
