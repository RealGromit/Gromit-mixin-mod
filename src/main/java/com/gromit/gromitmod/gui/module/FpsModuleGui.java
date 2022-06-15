package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.ClickDisableListener;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.module.fps.TntGui;
import com.gromit.gromitmod.module.fps.Tnt;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import lombok.Getter;

public class FpsModuleGui extends MainGui {

    @Getter private static FpsModuleGui instance;

    protected static final TextButton tnt = new TextButton(FontManager.getNormalSize(), mainGuiPointX + 7, mainGuiPointY + 39)
            .setButtonText("Tnt")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(TntGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(FpsModuleGui.getInstance()));

    private final ToggleButton tntStateButton = Tnt.getInstance().stateCheckbox;

    public FpsModuleGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();

        GlobalSaver.setFpsModuleGui(this);
        buttonList.add(tnt);
        buttonList.add(tntStateButton);
        fps.setState(true);
        crumbs.setState(false);
        other.setState(false);
        mods.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        tnt.drawButton(mouseX, mouseY);
        tntStateButton.drawButton(mouseX, mouseY);
        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, false, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
