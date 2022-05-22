package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.utils.RenderUtils;

public class CrumbsModuleGui extends MainGui {

    private static CrumbsModuleGui instance;

    protected static final TextButton explosionBox = new TextButton(6,4, "Explosion Box",
            (button) -> minecraft.displayGuiScreen(ExplosionBoxGui.getInstance()),
            (button) -> minecraft.displayGuiScreen(CrumbsModuleGui.getInstance()));

    private final CheckboxButton explosionBoxStateButton = ExplosionBox.getInstance().stateCheckbox;

    public CrumbsModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        Saver.setCrumbsModuleGui(this);
        explosionBox.updateButton(mainGuiPointX + 7, mainGuiPointY + 39, guiScale);
        explosionBoxStateButton.updateButton(mainGuiPointX + 49, mainGuiPointY + 39, guiScale);
        buttonList.add(explosionBox);
        buttonList.add(explosionBoxStateButton);
        crumbs.setState(true);
        fps.setState(false);
        fun.setState(false);
        render.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        explosionBox.drawButton(minecraft, mouseX, mouseY);
        explosionBoxStateButton.drawButton(minecraft, mouseX, mouseY);

        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, 255, 255, 255, 255);
    }
    @Override
    protected void setInstance() {
        instance = this;
    }

    public static CrumbsModuleGui getInstance() {
        return instance;
    }
}
