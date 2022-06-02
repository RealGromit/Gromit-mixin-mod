package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.RenderUtils;

public class RenderModuleGui extends MainGui {

    private static RenderModuleGui instance;

    public RenderModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        Saver.setRenderModuleGui(this);
        render.setState(true);
        crumbs.setState(false);
        fps.setState(false);
        fun.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, false, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static RenderModuleGui getInstance() {
        return instance;
    }
}
