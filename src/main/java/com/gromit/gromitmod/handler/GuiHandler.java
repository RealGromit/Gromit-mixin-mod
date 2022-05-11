package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.SettingsGui;
import com.gromit.gromitmod.gui.module.crumbs.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.FunModuleGui;
import com.gromit.gromitmod.gui.module.RenderModuleGui;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;

public class GuiHandler {

    public GuiHandler(GromitMod gromitMod) {
        new MainGui(gromitMod);
        new SettingsGui(gromitMod);
        new RenderModuleGui(gromitMod);
        new FpsModuleGui(gromitMod);
        new CrumbsModuleGui(gromitMod);
        new FunModuleGui(gromitMod);
        new ExplosionBoxGui(gromitMod);
    }
}