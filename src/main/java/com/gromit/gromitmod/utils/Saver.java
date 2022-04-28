package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.gui.AbstractGui;
import com.gromit.gromitmod.gui.AbstractModuleGui;

public class Saver {

    private static AbstractGui lastScreen;
    private static AbstractModuleGui lastModuleScreen;

    public static AbstractGui getLastScreen() {
        return lastScreen;
    }

    public static void setLastScreen(AbstractGui lastScreen) {
        Saver.lastScreen = lastScreen;
    }

    public static AbstractModuleGui getLastModuleScreen() {
        return lastModuleScreen;
    }

    public static void setLastModuleScreen(AbstractModuleGui lastModuleScreen) {
        Saver.lastModuleScreen = lastModuleScreen;
    }
}
