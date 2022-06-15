package com.gromit.gromitmod.gui.module.other;

import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.other.DebugBlock;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;

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

        FontManager.getTitleSize().drawString("Client Side Debug Block", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Get Info About Entities That Hit Selected Block", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Use Shovel To Select Block", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Debugblock Timeout : ", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());

        timeoutSlider.drawButton(mouseX, mouseY);
        FontManager.getNormalSize().drawString(timeoutSlider.buttonName, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
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