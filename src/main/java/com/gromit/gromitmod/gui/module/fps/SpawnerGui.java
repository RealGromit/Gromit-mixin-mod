package com.gromit.gromitmod.gui.module.fps;

import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.module.fps.Spawner;
import lombok.Getter;

import java.awt.*;

public class SpawnerGui extends FpsModuleGui {

    @Getter private static SpawnerGui instance;

    private final ToggleButton spawnerCheese = Spawner.getInstance().spawnerCheese;
    private final ToggleButton spawnerMobDisable = Spawner.getInstance().spawnerMobDisable;
    private final ToggleButton spawnerFireDisable = Spawner.getInstance().spawnerFireDisable;

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(spawnerCheese);
        buttonList.add(spawnerMobDisable);
        buttonList.add(spawnerFireDisable);
        spawner.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        title.drawString("Spawner", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Spawner cheese", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Disable Fire Particles", mainGuiPointX + 272, mainGuiPointY + 228, Color.WHITE.getRGB());
        normal.drawString("Disable Spawner Mob", mainGuiPointX + 272, mainGuiPointY + 256, Color.WHITE.getRGB());
        spawnerCheese.drawButton(mouseX, mouseY);
        spawnerMobDisable.drawButton(mouseX, mouseY);
        spawnerFireDisable.drawButton(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {spawner.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }
}
