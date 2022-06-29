package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.gui.AbstractGui;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.module.*;
import lombok.Getter;
import lombok.Setter;

public class GlobalSaver {

    @Getter @Setter private static AbstractGui lastAbstractGuiScreen;
    @Getter @Setter private static MainGui lastScreen;
    @Getter @Setter private static ModsModuleGui modsModuleGui;
    @Getter @Setter private static FpsModuleGui fpsModuleGui;
    @Getter @Setter private static CrumbsModuleGui crumbsModuleGui;
    @Getter @Setter private static OtherModuleGui otherModuleGui;
    @Getter @Setter private static SettingsGui settingsGui;
}