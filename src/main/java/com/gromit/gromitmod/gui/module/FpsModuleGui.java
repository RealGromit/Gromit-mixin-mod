package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import lombok.Getter;

public class FpsModuleGui extends MainGui {

    @Getter private static FpsModuleGui instance;

    public FpsModuleGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();

        GlobalSaver.setFpsModuleGui(this);
        fps.setState(true);
        crumbs.setState(false);
        other.setState(false);
        mods.setState(false);
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
}
