package com.gromit.gromitmod.gui.module.fun;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.module.FunModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.gui.slider.SmoothSlider;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.module.fun.AutoTick;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class AutoTickGui extends FunModuleGui {

    private static AutoTickGui instance;
    private final SmoothSlider tickslider = AutoTick.getInstance().tickslider;

    public AutoTickGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        tickslider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 80, guiScale);
        buttonList.add(tickslider);
        autoTickButton.setState(true);
        debugButton.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Auto Tick Repeaters", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Automatically Tick Repeaters When You Rightclick Them", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Ticks On Repeater:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());
        tickslider.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(tickslider.displayString, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {
        autoTickButton.setState(false);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static AutoTickGui getInstance() {
        return instance;
    }
}
