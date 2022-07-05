package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.TestGui;
import com.gromit.gromitmod.gui.module.*;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.gui.module.crumbs.PatchcrumbsGui;
import com.gromit.gromitmod.gui.module.fps.FallingBlockGui;
import com.gromit.gromitmod.gui.module.fps.SpawnerGui;
import com.gromit.gromitmod.gui.module.fps.TntGui;
import com.gromit.gromitmod.gui.module.other.AutoTickGui;
import com.gromit.gromitmod.gui.module.other.DebugBlockGui;
import com.gromit.gromitmod.gui.module.settings.CustomizationGui;
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
        new TntGui();
        new FallingBlockGui();
        new SpawnerGui();
        new SettingsGui();
        new CustomizationGui();
        new TestGui();
    }
}