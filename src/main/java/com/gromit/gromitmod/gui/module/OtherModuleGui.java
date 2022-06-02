package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.module.other.AutoTickGui;
import com.gromit.gromitmod.gui.module.other.DebugBlockGui;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.module.other.AutoTick;
import com.gromit.gromitmod.module.other.DebugBlock;
import com.gromit.gromitmod.utils.RenderUtils;

public class OtherModuleGui extends MainGui {

    private static OtherModuleGui instance;
    private static final GromitMod gromitMod = GromitMod.getInstance();

    protected static final TextButton autoTickButton = new TextButton(gromitMod.getNewButtonId(), 4, "Auto Tick",
            button -> minecraft.displayGuiScreen(AutoTickGui.getInstance()),
            button -> minecraft.displayGuiScreen(OtherModuleGui.getInstance()));

    protected static final TextButton debugButton = new TextButton(gromitMod.getNewButtonId(),4, "Debug Block",
            button -> minecraft.displayGuiScreen(DebugBlockGui.getInstance()),
            button -> minecraft.displayGuiScreen(OtherModuleGui.getInstance()));

    private final CheckboxButton autoTickStateButton = AutoTick.getInstance().stateCheckbox;
    private final CheckboxButton debugBlockStateButton = DebugBlock.getInstance().stateCheckbox;

    public OtherModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        GlobalSaver.setOtherModuleGui(this);
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

        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, false, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static OtherModuleGui getInstance() {
        return instance;
    }
}
