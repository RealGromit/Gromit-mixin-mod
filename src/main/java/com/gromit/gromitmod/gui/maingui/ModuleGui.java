package com.gromit.gromitmod.gui.maingui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.AbstractModuleGui;
import com.gromit.gromitmod.gui.button.ScrollableButton;
import net.minecraft.client.gui.GuiButton;

public class ModuleGui extends AbstractModuleGui {

    public ModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (GuiButton textButton : buttonList) {
            if (textButton instanceof ScrollableButton) continue;
            textButton.drawButton(minecraft, mouseX, mouseY);
        }
    }
}
