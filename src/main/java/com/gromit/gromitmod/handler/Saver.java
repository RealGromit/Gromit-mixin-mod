package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.subgui.CrumbsModuleGui;
import com.gromit.gromitmod.gui.subgui.FpsModuleGui;
import com.gromit.gromitmod.gui.subgui.FunModuleGui;
import com.gromit.gromitmod.gui.subgui.RenderModuleGui;

public class Saver {

    private static MainGui lastScreen;
    private static RenderModuleGui renderModuleGui;
    private static FpsModuleGui fpsModuleGui;
    private static CrumbsModuleGui crumbsModuleGui;
    private static FunModuleGui funModuleGui;

    public static MainGui getLastScreen() {
        return lastScreen;
    }

    public static void setLastScreen(MainGui lastScreen) {
        Saver.lastScreen = lastScreen;
    }


    public static RenderModuleGui getRenderModuleGui() {
        return renderModuleGui;
    }

    public static void setRenderModuleGui(RenderModuleGui lastModuleScreen) {
        Saver.renderModuleGui = lastModuleScreen;
    }

    public static FpsModuleGui getFpsModuleGui() {
        return fpsModuleGui;
    }

    public static void setFpsModuleGui(FpsModuleGui fpsModuleGui) {
        Saver.fpsModuleGui = fpsModuleGui;
    }

    public static CrumbsModuleGui getCrumbsModuleGui() {
        return crumbsModuleGui;
    }

    public static void setCrumbsModuleGui(CrumbsModuleGui crumbsModuleGui) {
        Saver.crumbsModuleGui = crumbsModuleGui;
    }

    public static FunModuleGui getFunModuleGui() {
        return funModuleGui;
    }

    public static void setFunModuleGui(FunModuleGui funModuleGui) {
        Saver.funModuleGui = funModuleGui;
    }
}
