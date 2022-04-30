package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.module.other.RangeCalcModule;

public class ModuleHandler {

    private final GromitMod gromitMod;

    private final RangeCalcModule rangeCalcModule;

    public ModuleHandler(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        rangeCalcModule = new RangeCalcModule(gromitMod);
    }

    public RangeCalcModule getRangeCalcModule() {
        return rangeCalcModule;
    }
}
