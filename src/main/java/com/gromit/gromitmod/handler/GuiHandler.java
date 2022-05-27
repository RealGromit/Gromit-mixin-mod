package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.SettingsGui;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.module.RenderModuleGui;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.gui.module.crumbs.PatchcrumbsGui;
import com.gromit.gromitmod.gui.module.other.AutoTickGui;
import com.gromit.gromitmod.gui.module.other.DebugBlockGui;

public class GuiHandler {

    public GuiHandler(GromitMod gromitMod) {
        new MainGui(gromitMod);
        new SettingsGui(gromitMod);
        new RenderModuleGui(gromitMod);
        new FpsModuleGui(gromitMod);
        new CrumbsModuleGui(gromitMod);
        new OtherModuleGui(gromitMod);
        new ExplosionBoxGui(gromitMod);
        new AutoTickGui(gromitMod);
        new DebugBlockGui(gromitMod);
        new PatchcrumbsGui(gromitMod);
    }
}