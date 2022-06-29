package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.ClickDisableListener;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.module.settings.CustomizationGui;
import com.gromit.gromitmod.module.settings.Customizations;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import lombok.Getter;

public class SettingsGui extends MainGui {

    @Getter private static SettingsGui instance;

    protected static final TextButton customization = new TextButton(normal, mainGuiPointX + 28, mainGuiPointY + 156)
            .setButtonText("Customizations")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(CustomizationGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(SettingsGui.getInstance()));

    private final ToggleButton customizationsStateButton = Customizations.getInstance().stateCheckbox;

    @Override
    public void initGui() {
        super.initGui();

        GlobalSaver.setSettingsGui(this);
        settings.setState(true);
        other.setState(false);
        crumbs.setState(false);
        fps.setState(false);
        mods.setState(false);

        buttonList.add(customization);
        buttonList.add(customizationsStateButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        customization.drawButton(mouseX, mouseY);
        customizationsStateButton.drawButton(mouseX, mouseY);
        RenderUtils.drawLine(mainGuiPointX + 240, mainGuiPointY + 148, 0, 404, 4, false, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
