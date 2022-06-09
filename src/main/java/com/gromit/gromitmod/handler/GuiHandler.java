package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.ModsModuleGui;
import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.gui.module.crumbs.PatchcrumbsGui;
import com.gromit.gromitmod.gui.module.other.AutoTickGui;
import com.gromit.gromitmod.gui.module.other.DebugBlockGui;
import com.gromit.gromitmod.gui.schematica.SchematicLoadGui;

public class GuiHandler {

    public GuiHandler() {
        new MainGui();
        new ModsModuleGui();
        new FpsModuleGui();
        new CrumbsModuleGui();
        new OtherModuleGui();
        new ExplosionBoxGui();
        new AutoTickGui();
        new DebugBlockGui();
        new PatchcrumbsGui();
        new SchematicLoadGui();
    }
}