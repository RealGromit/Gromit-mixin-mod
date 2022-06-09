package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.listener.ClickDisableListener;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.module.other.AutoTickGui;
import com.gromit.gromitmod.gui.module.other.DebugBlockGui;
import com.gromit.gromitmod.module.other.AutoTick;
import com.gromit.gromitmod.module.other.DebugBlock;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import lombok.Getter;

public class OtherModuleGui extends MainGui {

    @Getter private static OtherModuleGui instance;

    protected static final TextButton autoTick = new TextButton(FontUtil.normal, mainGuiPointX + 7, mainGuiPointY + 39)
            .setButtonText("Auto Tick")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(AutoTickGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(OtherModuleGui.getInstance()));

    protected static final TextButton debug = new TextButton(FontUtil.normal, mainGuiPointX + 7, mainGuiPointY + 46)
            .setButtonText("Debug Block")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(DebugBlockGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(OtherModuleGui.getInstance()));

    private final CheckboxButton autoTickStateButton = AutoTick.getInstance().stateCheckbox;
    private final CheckboxButton debugBlockStateButton = DebugBlock.getInstance().stateCheckbox;

    public OtherModuleGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();

        GlobalSaver.setOtherModuleGui(this);
        other.setState(true);
        crumbs.setState(false);
        fps.setState(false);
        mods.setState(false);

        buttonList.add(autoTick);
        buttonList.add(autoTickStateButton);
        buttonList.add(debug);
        buttonList.add(debugBlockStateButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        autoTick.drawButton(mouseX, mouseY);
        autoTickStateButton.drawButton(mouseX, mouseY);
        debug.drawButton(mouseX, mouseY);
        debugBlockStateButton.drawButton(mouseX, mouseY);

        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, false, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
