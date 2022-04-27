package com.gromit.gromitmod.utils;

import net.minecraft.client.gui.GuiScreen;

public class Saver {

    private static GuiScreen lastScreen;

    public static GuiScreen getLastScreen() {
        return lastScreen;
    }

    public static void setLastScreen(GuiScreen lastScreen) {
        Saver.lastScreen = lastScreen;
    }
}
