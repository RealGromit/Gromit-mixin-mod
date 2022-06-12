package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.ClickDisableListener;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.module.crumbs.ExplosionBoxGui;
import com.gromit.gromitmod.gui.module.crumbs.PatchcrumbsGui;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.module.crumbs.Patchcrumbs;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import lombok.Getter;

public class CrumbsModuleGui extends MainGui {

    @Getter private static CrumbsModuleGui instance;

    protected static final TextButton explosionBox = new TextButton(FontUtil.normal, mainGuiPointX + 7, mainGuiPointY + 39)
            .setButtonText("Explosion Box")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(ExplosionBoxGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(CrumbsModuleGui.getInstance()));

    protected static final TextButton patchcrumbs = new TextButton(FontUtil.normal, mainGuiPointX + 7, mainGuiPointY + 46)
            .setButtonText("Patchcrumbs")
            .addButtonListener((ClickEnableListener) button -> minecraft.displayGuiScreen(PatchcrumbsGui.getInstance()))
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(CrumbsModuleGui.getInstance()));

    private final ToggleButton explosionBoxStateButton = ExplosionBox.getInstance().stateCheckbox;
    private final ToggleButton patchcrumbsStateButton = Patchcrumbs.getInstance().stateCheckbox;

    public CrumbsModuleGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();

        GlobalSaver.setCrumbsModuleGui(this);
        buttonList.add(explosionBox);
        buttonList.add(patchcrumbs);
        buttonList.add(explosionBoxStateButton);
        buttonList.add(patchcrumbsStateButton);
        crumbs.setState(true);
        fps.setState(false);
        other.setState(false);
        mods.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        explosionBox.drawButton(mouseX, mouseY);
        patchcrumbs.drawButton(mouseX, mouseY);
        explosionBoxStateButton.drawButton(mouseX, mouseY);
        patchcrumbsStateButton.drawButton(mouseX, mouseY);

        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 4, false, 255, 255, 255, 255);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
