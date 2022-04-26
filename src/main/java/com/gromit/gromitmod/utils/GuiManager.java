package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.maingui.MainGui;
import com.gromit.gromitmod.gui.maingui.ModuleGui;
import com.gromit.gromitmod.gui.subgui.RangeCalcModuleGui;

public class GuiManager {

    private final MainGui mainGui;
    private final ModuleGui moduleGui;
    private final RangeCalcModuleGui rangeCalcModuleGui;

    public GuiManager(GromitMod gromitMod) {
        mainGui = new MainGui(gromitMod);
        moduleGui = new ModuleGui(gromitMod);
        rangeCalcModuleGui = new RangeCalcModuleGui(gromitMod);
    }

    public MainGui getMainGui() {
        return mainGui;
    }

    public ModuleGui getModuleGui() {
        return moduleGui;
    }

    public RangeCalcModuleGui getRangeCalcModuleGui() {
        return rangeCalcModuleGui;
    }
}
