package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.gui.module.crumbs.PatchcrumbsGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.module.crumbs.Patchcrumbs;
import com.gromit.gromitmod.utils.RenderUtils;

public class CrumbsModuleGui extends MainGui {

    private static CrumbsModuleGui instance;

    protected static final TextButton explosionBox = new TextButton(0,4, "Explosion Box",
            button -> minecraft.displayGuiScreen(ExplosionBoxGui.getInstance()),
            button -> minecraft.displayGuiScreen(CrumbsModuleGui.getInstance()));

    protected static final TextButton patchcrumbs = new TextButton(0,4, "Patchcrumbs",
            button -> minecraft.displayGuiScreen(PatchcrumbsGui.getInstance()),
            button -> minecraft.displayGuiScreen(CrumbsModuleGui.getInstance()));

    private final CheckboxButton explosionBoxStateButton = ExplosionBox.getInstance().stateCheckbox;
    private final CheckboxButton patchcrumbsStateButton = Patchcrumbs.getInstance().stateCheckbox;

    public CrumbsModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        Saver.setCrumbsModuleGui(this);
        explosionBox.updateButton(mainGuiPointX + 7, mainGuiPointY + 39, guiScale);
        patchcrumbs.updateButton(mainGuiPointX + 7, mainGuiPointY + 46, guiScale);
        explosionBoxStateButton.updateButton(mainGuiPointX + 49, mainGuiPointY + 39, guiScale);
        patchcrumbsStateButton.updateButton(mainGuiPointX + 49, mainGuiPointY + 46, guiScale);
        buttonList.add(explosionBox);
        buttonList.add(patchcrumbs);
        buttonList.add(explosionBoxStateButton);
        buttonList.add(patchcrumbsStateButton);
        crumbs.setState(true);
        fps.setState(false);
        fun.setState(false);
        render.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        explosionBox.drawButton(minecraft, mouseX, mouseY);
        patchcrumbs.drawButton(minecraft, mouseX, mouseY);
        explosionBoxStateButton.drawButton(minecraft, mouseX, mouseY);
        patchcrumbsStateButton.drawButton(minecraft, mouseX, mouseY);

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
