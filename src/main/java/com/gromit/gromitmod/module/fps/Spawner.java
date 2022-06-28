package com.gromit.gromitmod.module.fps;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.module.AbstractModule;
import lombok.Getter;

@Module(moduleName = "SpawnerModule")
public class Spawner extends AbstractModule {

    @Getter private static Spawner instance;

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 194, MainGui.mainGuiPointY + 214);

    public final ToggleButton spawnerCheese = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 201);
    public final ToggleButton spawnerFireDisable = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 229);
    public final ToggleButton spawnerMobDisable = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 257);

    public Spawner() {instance = this;}
}
