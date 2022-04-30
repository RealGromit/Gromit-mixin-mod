package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.ModuleGui;

public class Saver {

    private static MainGui lastScreen;
    private static ModuleGui lastModuleScreen;

    public static MainGui getLastScreen() {
        return lastScreen;
    }

    public static void setLastScreen(MainGui lastScreen) {
        Saver.lastScreen = lastScreen;
    }

    public static ModuleGui getLastModuleScreen() {
        return lastModuleScreen;
    }

    public static void setLastModuleScreen(ModuleGui lastModuleScreen) {
        Saver.lastModuleScreen = lastModuleScreen;
    }
}
