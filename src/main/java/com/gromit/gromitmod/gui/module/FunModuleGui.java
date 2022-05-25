package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.module.fun.AutoTickGui;
import com.gromit.gromitmod.gui.module.fun.DebugBlockGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.module.fun.AutoTick;
import com.gromit.gromitmod.module.fun.DebugBlock;
import com.gromit.gromitmod.utils.RenderUtils;

import java.io.IOException;

public class FunModuleGui extends MainGui {

    private static FunModuleGui instance;

    protected static final TextButton autoTickButton = new TextButton(1,4, "AutoTick",
            button -> minecraft.displayGuiScreen(AutoTickGui.getInstance()),
            button -> minecraft.displayGuiScreen(FunModuleGui.getInstance()));

    protected static final TextButton debugButton = new TextButton(2,4, "Debug Block",
            button -> minecraft.displayGuiScreen(DebugBlockGui.getInstance()),
            button -> minecraft.displayGuiScreen(FunModuleGui.getInstance()));

    private final CheckboxButton autoTickStateButton = AutoTick.getInstance().stateCheckbox;
    private final CheckboxButton debugBlockStateButton = DebugBlock.getInstance().stateCheckbox;

    public FunModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        Saver.setFunModuleGui(this);
        fun.setState(true);
        crumbs.setState(false);
        fps.setState(false);
        render.setState(false);

        autoTickButton.updateButton(mainGuiPointX + 7, mainGuiPointY + 39, guiScale);
        autoTickStateButton.updateButton(mainGuiPointX + 49, mainGuiPointY + 39, guiScale);
        debugButton.updateButton(mainGuiPointX + 7, mainGuiPointY + 46, guiScale);
        debugBlockStateButton.updateButton(mainGuiPointX + 49, mainGuiPointY + 46, guiScale);
        buttonList.add(autoTickButton);
        buttonList.add(autoTickStateButton);
        buttonList.add(debugButton);
        buttonList.add(debugBlockStateButton);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        autoTickButton.drawButton(minecraft, mouseX, mouseY);
        autoTickStateButton.drawButton(minecraft, mouseX, mouseY);
        debugButton.drawButton(minecraft, mouseX, mouseY);
        debugBlockStateButton.drawButton(minecraft, mouseX, mouseY);

        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, 255, 255, 255, 255);
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static FunModuleGui getInstance() {
        return instance;
    }
}
