package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.module.render.ExplosionBox;

public class ModuleHandler {

    public ModuleHandler(GromitMod gromitMod) {
        new ExplosionBox(gromitMod);
    }
}