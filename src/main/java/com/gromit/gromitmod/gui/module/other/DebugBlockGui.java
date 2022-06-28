package com.gromit.gromitmod.gui.module.other;

import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.other.DebugBlock;

import java.awt.Color;

public class DebugBlockGui extends OtherModuleGui {

    private static DebugBlockGui instance;
    private final Slider timeoutSlider = DebugBlock.getInstance().timeoutSlider;

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

        title.drawString("Client Side Debug Block", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Get Info About Entities That Hit Selected Block", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Use Shovel To Select Block", mainGuiPointX + 272, mainGuiPointY + 228, Color.WHITE.getRGB());
        normal.drawString("Debugblock Timeout : ", mainGuiPointX + 272, mainGuiPointY + 300, Color.WHITE.getRGB());

        timeoutSlider.drawButton(mouseX, mouseY);
        normal.drawString(timeoutSlider.buttonName, mainGuiPointX + 488, mainGuiPointY + 300, Color.WHITE.getRGB());
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