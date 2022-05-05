package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.FunModuleGui;
import com.gromit.gromitmod.gui.module.RenderModuleGui;

public class Saver {

    private static MainGui lastScreen;
    private static RenderModuleGui renderModuleGui;
    private static FpsModuleGui fpsModuleGui;
    private static CrumbsModuleGui crumbsModuleGui;
    private static FunModuleGui funModuleGui;
    private static int openGuiButton = -1;

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

    public static int getOpenGuiButton() {
        return openGuiButton;
    }

    public static void setOpenGuiButton(int openGuiButton) {
        Saver.openGuiButton = openGuiButton;
    }
}
