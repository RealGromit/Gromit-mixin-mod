package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.maingui.MainGui;
import com.gromit.gromitmod.gui.maingui.ModuleGui;
import com.gromit.gromitmod.gui.maingui.SettingsGui;
import com.gromit.gromitmod.gui.subgui.RangeCalcModuleGui;

public class GuiManager {

    private final MainGui mainGui;
    private final SettingsGui settingsGui;
    private final ModuleGui moduleGui;
    private final RangeCalcModuleGui rangeCalcModuleGui;

    public GuiManager(GromitMod gromitMod) {
        mainGui = new MainGui(gromitMod);
        settingsGui = new SettingsGui(gromitMod);
        moduleGui = new ModuleGui(gromitMod);
        rangeCalcModuleGui = new RangeCalcModuleGui(gromitMod);
    }

    public MainGui getMainGui() {
        return mainGui;
    }

    public SettingsGui getSettingsGui() {
        return settingsGui;
    }

    public ModuleGui getModuleGui() {
        return moduleGui;
    }

    public RangeCalcModuleGui getRangeCalcModuleGui() {
        return rangeCalcModuleGui;
    }
}
