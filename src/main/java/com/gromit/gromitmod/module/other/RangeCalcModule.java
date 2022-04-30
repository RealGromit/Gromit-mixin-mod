package com.gromit.gromitmod.module.other;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.module.AbstractModule;
import net.minecraft.client.Minecraft;

public class RangeCalcModule extends AbstractModule {

    private final GromitMod gromitMod;
    private final Minecraft minecraft;

    public RangeCalcModule(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
    }


}