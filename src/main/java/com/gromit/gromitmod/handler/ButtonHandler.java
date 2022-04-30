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

    private final GromitMod gromitMod;
    private final Minecraft minecraft;

    private final AbstractGuiButtons abstractGuiButtons;
    private final AbstractModuleGuiButtons abstractModuleGuiButtons;
    private final RangeCalcModuleGuiButtons rangeCalcModuleGuiButtons;

    public ButtonHandler(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
        abstractGuiButtons = new AbstractGuiButtons();
        abstractModuleGuiButtons = new AbstractModuleGuiButtons();
        rangeCalcModuleGuiButtons = new RangeCalcModuleGuiButtons();
    }

    public AbstractGuiButtons getAbstractGuiButtons() {
        return abstractGuiButtons;
    }

    public AbstractModuleGuiButtons getAbstractModuleGuiButtons() {
        return abstractModuleGuiButtons;
    }

    public RangeCalcModuleGuiButtons getRangeCalcModuleGuiButtons() {
        return rangeCalcModuleGuiButtons;
    }

    public class AbstractGuiButtons {

        private final TextButton modules = new TextButton(0, 0, 0, (int) (FontUtil.normal.getStringWidth("Modules") / 1.9), 4, "Modules", 1,
                () -> {
                if (Saver.getLastModuleScreen() != null) minecraft.displayGuiScreen(Saver.getLastModuleScreen());
                else minecraft.displayGuiScreen(gromitMod.getGuiManager().getModuleGui());
        });
        private final TextButton settings = new TextButton(1, 0, 0, (int) ((int) FontUtil.normal.getStringWidth("Settings") / 1.9), 4, "Settings", 1,
                () -> minecraft.displayGuiScreen(gromitMod.getGuiManager().getSettingsGui()));

        public TextButton getModules() {
            return modules;
        }

        public TextButton getSettings() {
            return settings;
        }
    }

    public class AbstractModuleGuiButtons {

        private final ScrollableButton rangeCalc = new ScrollableButton(2, 0, 0, (int) (FontUtil.normal.getStringWidth("Range Calc") / 1.9), 4, "Range Calc", 1,
                () -> minecraft.displayGuiScreen(gromitMod.getGuiManager().getRangeCalcModuleGui()));

        public ScrollableButton getRangeCalc() {
            return rangeCalc;
        }
    }

    public class RangeCalcModuleGuiButtons {

        private final ChangeableButton stateButton = new ChangeableButton(3, 0, 0, (int) FontUtil.normal.getStringWidth("disabled") / 2, 4, "disabled", "enabled", 1,
                () -> gromitMod.getModuleHandler().getRangeCalcModule().register(),
                () -> gromitMod.getModuleHandler().getRangeCalcModule().unregister());
        private final Slider slider = new Slider(4, 0, 0, 80, 2, "", 1, 100, 1);

        public ChangeableButton getStateButton() {
            return stateButton;
        }

        public Slider getSlider() {
            return slider;
        }
    }
}

