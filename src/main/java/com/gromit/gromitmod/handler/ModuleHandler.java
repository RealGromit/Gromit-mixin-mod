package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.module.other.RangeCalcModule;

public class ModuleHandler {

    private static final GromitMod gromitMod = GromitMod.INSTANCE;

    private static final RangeCalcModule rangeCalcModule = new RangeCalcModule(gromitMod);

    public static RangeCalcModule getRangeCalcModule() {
        return rangeCalcModule;
    }
}
