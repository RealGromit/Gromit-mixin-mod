package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.SettingsGui;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.FunModuleGui;
import com.gromit.gromitmod.gui.module.RenderModuleGui;

public class GuiHandler {

    private static final GromitMod gromitMod = GromitMod.INSTANCE;

    private static final MainGui mainGui = new MainGui(gromitMod);
    private static final SettingsGui settingsGui = new SettingsGui(gromitMod);
    private static final RenderModuleGui renderModuleGui = new RenderModuleGui(gromitMod);
    private static final FpsModuleGui fpsModuleGui = new FpsModuleGui(gromitMod);
    private static final CrumbsModuleGui crumbsModuleGui = new CrumbsModuleGui(gromitMod);
    private static final FunModuleGui funModuleGui = new FunModuleGui(gromitMod);

    public static MainGui getMainGui() {
        return mainGui;
    }

    public static SettingsGui getSettingsGui() {
        return settingsGui;
    }

    public static RenderModuleGui getRenderModuleGui() {
        return renderModuleGui;
    }

    public static FpsModuleGui getFpsModuleGui() {
        return fpsModuleGui;
    }

    public static CrumbsModuleGui getCrumbsModuleGui() {
        return crumbsModuleGui;
    }

    public static FunModuleGui getFunModuleGui() {
        return funModuleGui;
    }
}
