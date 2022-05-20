package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.RenderUtils;

public class FunModuleGui extends MainGui {

    private static FunModuleGui instance;

    public FunModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        Saver.setFunModuleGui(this);
        fun.getPersistBoolean().setState(true);
        crumbs.getPersistBoolean().setState(false);
        fps.getPersistBoolean().setState(false);
        render.getPersistBoolean().setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static FunModuleGui getInstance() {
        return instance;
    }
}
