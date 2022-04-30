package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ChangeableButton;
import com.gromit.gromitmod.gui.button.ScrollableButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

//Created for better interactability with gui classes
public class ButtonHandler {

    private static final GromitMod gromitMod = GromitMod.INSTANCE;
    private static final Minecraft minecraft = gromitMod.getMinecraft();

    private static final TextButton modules = new TextButton(0, 0, 0, (int) (FontUtil.normal.getStringWidth("Modules") / 1.9), 4, "Modules", 1,
            () -> {
                if (Saver.getLastModuleScreen() != null) minecraft.displayGuiScreen(Saver.getLastModuleScreen());
                else minecraft.displayGuiScreen(GuiHandler.getModuleGui());
            });

    private static final TextButton settings = new TextButton(1, 0, 0, (int) ((int) FontUtil.normal.getStringWidth("Settings") / 1.9), 4, "Settings", 1,
            () -> minecraft.displayGuiScreen(GuiHandler.getSettingsGui()));

    public static TextButton getModules() {
        return modules;
    }

    public static TextButton getSettings() {
        return settings;
    }

    private static final ScrollableButton rangeCalc = new ScrollableButton(2, 0, 0, (int) (FontUtil.normal.getStringWidth("Range Calc") / 1.9), 4, "Range Calc", 1,
            () -> minecraft.displayGuiScreen(GuiHandler.getRangeCalcModuleGui()));

    public static ScrollableButton getRangeCalc() {
        return rangeCalc;
    }

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

