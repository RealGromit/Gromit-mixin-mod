package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.maingui.MainGui;
import com.gromit.gromitmod.gui.maingui.ModuleGui;
import com.gromit.gromitmod.gui.maingui.SettingsGui;
import com.gromit.gromitmod.gui.subgui.RangeCalcModuleGui;

public class GuiHandler {

    private static final GromitMod gromitMod = GromitMod.INSTANCE;

    private static final MainGui mainGui = new MainGui(gromitMod);
    private static final SettingsGui settingsGui = new SettingsGui(gromitMod);
    private static final ModuleGui moduleGui = new ModuleGui(gromitMod);
    private static final RangeCalcModuleGui rangeCalcModuleGui = new RangeCalcModuleGui(gromitMod);

    public static MainGui getMainGui() {
        return mainGui;
    }

    public static SettingsGui getSettingsGui() {
        return settingsGui;
    }

    public static ModuleGui getModuleGui() {
        return moduleGui;
    }

    public static RangeCalcModuleGui getRangeCalcModuleGui() {
        return rangeCalcModuleGui;
    }
}
