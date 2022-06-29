package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.ClickDisableListener;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.module.fps.FallingBlockGui;
import com.gromit.gromitmod.gui.module.fps.SpawnerGui;
import com.gromit.gromitmod.gui.module.fps.TntGui;
import com.gromit.gromitmod.module.fps.FallingBlock;
import com.gromit.gromitmod.module.fps.Spawner;
import com.gromit.gromitmod.module.fps.Tnt;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import lombok.Getter;

public class FpsModuleGui extends MainGui {

    @Getter private static FpsModuleGui instance;

    protected static final TextButton tnt = new TextButton(normal, mainGuiPointX + 28, mainGuiPointY + 156)
            .setButtonText("Tnt")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(TntGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(FpsModuleGui.getInstance()));

    protected static final TextButton sand = new TextButton(normal, mainGuiPointX + 28, mainGuiPointY + 184)
            .setButtonText("Falling Block")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(FallingBlockGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(FpsModuleGui.getInstance()));

    protected static final TextButton spawner = new TextButton(normal, mainGuiPointX + 28, mainGuiPointY + 212)
            .setButtonText("Spawner")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(SpawnerGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(FpsModuleGui.getInstance()));

    private final ToggleButton tntStateButton = Tnt.getInstance().stateCheckbox;
    private final ToggleButton sandStateButton = FallingBlock.getInstance().stateCheckbox;
    private final ToggleButton spawnerStateButton = Spawner.getInstance().stateCheckbox;

    public FpsModuleGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();

        GlobalSaver.setFpsModuleGui(this);
        buttonList.add(tnt);
        buttonList.add(tntStateButton);
        buttonList.add(sand);
        buttonList.add(sandStateButton);
        buttonList.add(spawner);
        buttonList.add(spawnerStateButton);
        fps.setState(true);
        crumbs.setState(false);
        other.setState(false);
        mods.setState(false);
        settings.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        tnt.drawButton(mouseX, mouseY);
        tntStateButton.drawButton(mouseX, mouseY);
        sand.drawButton(mouseX, mouseY);
        sandStateButton.drawButton(mouseX, mouseY);
        spawner.drawButton(mouseX, mouseY);
        spawnerStateButton.drawButton(mouseX, mouseY);
        RenderUtils.drawLine(mainGuiPointX + 240, mainGuiPointY + 148, 0, 404, 4, false, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
