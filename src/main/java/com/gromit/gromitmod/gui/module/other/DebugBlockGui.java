package com.gromit.gromitmod.gui.module.other;

import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.other.DebugBlock;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.Color;

public class DebugBlockGui extends OtherModuleGui {

    private static DebugBlockGui instance;
    private final Slider timeoutSlider = DebugBlock.getInstance().timeoutSlider;

    public DebugBlockGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(timeoutSlider);
        debug.setState(true);
        autoTick.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Client Side Debug Block", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Get Info About Entities That Hit Selected Block", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Use Shovel To Select Block", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Debugblock Timeout : ", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());

        timeoutSlider.drawButton(mouseX, mouseY);
        FontUtil.normal.drawString(timeoutSlider.buttonName, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {
        autoTick.setState(false);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static DebugBlockGui getInstance() {
        return instance;
    }
}