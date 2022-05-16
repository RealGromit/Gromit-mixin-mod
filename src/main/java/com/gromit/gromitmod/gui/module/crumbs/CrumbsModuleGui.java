package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.utils.RenderUtils;

public class CrumbsModuleGui extends MainGui {

    private static CrumbsModuleGui instance;

    protected static final TextButton explosionBox = new TextButton(6, 0, 0, 4, "Explosion Box",
            (button) -> minecraft.displayGuiScreen(ExplosionBoxGui.getInstance()),
            (button) -> minecraft.displayGuiScreen(CrumbsModuleGui.getInstance()));

    private static final CheckboxButton checkbox = new CheckboxButton(11, 0, 0, 4, 4,
            (button) -> ExplosionBox.getInstance().register(),
            (button) -> ExplosionBox.getInstance().unregister());

    public CrumbsModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        Saver.setCrumbsModuleGui(this);
        explosionBox.updateButton(mainGuiPointX + 12, mainGuiPointY + 39, guiScale);
        checkbox.updateButton(mainGuiPointX + 54, mainGuiPointY + 39, guiScale);
        buttonList.add(explosionBox);
        buttonList.add(checkbox);
        crumbs.setState(true);
        fps.setState(false);
        fun.setState(false);
        render.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        explosionBox.drawButton(minecraft, mouseX, mouseY);
        checkbox.drawButton(minecraft, mouseX, mouseY);

        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 138, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
    }
    @Override
    protected void setInstance() {
        instance = this;
    }

    public static CrumbsModuleGui getInstance() {
        return instance;
    }
}
