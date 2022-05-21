package com.gromit.gromitmod.gui.module.fun;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.module.FunModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.gui.slider.SmoothSlider;
import com.gromit.gromitmod.module.fun.AutoTick;
import com.gromit.gromitmod.module.fun.DebugBlock;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class DebugBlockGui extends FunModuleGui {

    private static DebugBlockGui instance;
    public static final SmoothSlider timeoutslider = DebugBlock.getInstance().timeoutslider;

    public DebugBlockGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        timeoutslider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 80, guiScale);
        buttonList.add(timeoutslider);

        debugButton.setState(true);
        autoTickButton.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Client Side Debug Block", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Get Info About Entities That Hit Selected Block", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Use Shovel To Select Block", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Debugblock Timeout : ", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());

        timeoutslider.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(timeoutslider.displayString, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {
        autoTickButton.setState(false);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static DebugBlockGui getInstance() {
        return instance;
    }
}