package com.gromit.gromitmod.handler.buttonhandler.subgui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ChangeableButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.handler.ModuleHandler;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

public class RangeCalcModuleButtons {

    private static final Minecraft minecraft = GromitMod.INSTANCE.getMinecraft();

    private static final ChangeableButton stateButton = new ChangeableButton(3, 0, 0, (int) FontUtil.normal.getStringWidth("disabled") / 2, 4, "disabled", "enabled", 1,
            () -> ModuleHandler.getRangeCalcModule().register(),
            () -> ModuleHandler.getRangeCalcModule().unregister());

    private static final Slider slider = new Slider(4, 0, 0, 80, 2, "", 1, 100, 1);

    public static ChangeableButton getStateButton() {
        return stateButton;
    }

    public static Slider getSlider() {
        return slider;
    }
}
