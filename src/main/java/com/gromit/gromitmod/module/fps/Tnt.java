package com.gromit.gromitmod.module.fps;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.StateDisableListener;
import com.gromit.gromitmod.gui.button.listener.StateEnableListener;
import com.gromit.gromitmod.module.AbstractModule;
import lombok.Getter;

@Module(moduleName = "TntModule")
public class Tnt extends AbstractModule {

    @Getter private static Tnt instance;

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 48.5f, MainGui.mainGuiPointY + 39.8f)
            .addButtonListener((StateEnableListener) button -> register())
            .addButtonListener((StateDisableListener) button -> unregister());

    public final ToggleButton tntFlash = new ToggleButton(MainGui.mainGuiPointX + 129.5f, MainGui.mainGuiPointY + 50.3f);
    public final ToggleButton tntXRay = new ToggleButton(MainGui.mainGuiPointX + 129.5f, MainGui.mainGuiPointY + 57.3f);
    public final ToggleButton tntSwell = new ToggleButton(MainGui.mainGuiPointX + 129.5f, MainGui.mainGuiPointY + 64.3f);

    public Tnt() {
        instance = this;
    }
}