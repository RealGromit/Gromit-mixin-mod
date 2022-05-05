package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

public class ModuleGui extends MainGui {

    protected int scroll;

    protected static final TextButton render = new TextButton(2, 0, 0, (int) FontUtil.normal.getStringWidth("Render") / 2, 4, "Render", 1,
            () -> {
                if (Saver.getRenderModuleGui() != null) minecraft.displayGuiScreen(Saver.getRenderModuleGui());
                else minecraft.displayGuiScreen(GuiHandler.getRenderModuleGui());
            });

    protected static final TextButton fps = new TextButton(3, 0, 0, (int) FontUtil.normal.getStringWidth("Fps") / 2, 4, "Fps", 1,
            () -> {
                if (Saver.getFpsModuleGui() != null) minecraft.displayGuiScreen(Saver.getFpsModuleGui());
                else minecraft.displayGuiScreen(GuiHandler.getFpsModuleGui());
            });

    protected static final TextButton crumbs = new TextButton(4, 0, 0, (int) FontUtil.normal.getStringWidth("Crumbs") / 2, 4, "Crumbs", 1,
            () -> {
                if (Saver.getCrumbsModuleGui() != null) minecraft.displayGuiScreen(Saver.getCrumbsModuleGui());
                else minecraft.displayGuiScreen(GuiHandler.getCrumbsModuleGui());
            });

    protected static final TextButton fun = new TextButton(5, 0, 0, (int) FontUtil.normal.getStringWidth("Fun") / 2, 4, "Fun", 1,
            () -> {
                if (Saver.getFunModuleGui() != null) minecraft.displayGuiScreen(Saver.getFunModuleGui());
                else minecraft.displayGuiScreen(GuiHandler.getFunModuleGui());
            });

    public ModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        render.updateButton(mainGuiPointX + 87, mainGuiPointY + 26, guiScale);
        fps.updateButton(mainGuiPointX + 126, mainGuiPointY + 26, guiScale);
        crumbs.updateButton(mainGuiPointX + 156, mainGuiPointY + 26, guiScale);
        fun.updateButton(mainGuiPointX + 198, mainGuiPointY + 26, guiScale);
        buttonList.add(render);
        buttonList.add(fps);
        buttonList.add(crumbs);
        buttonList.add(fun);
        modules.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        render.drawButton(minecraft, mouseX, mouseY);
        fps.drawButton(minecraft, mouseX, mouseY);
        crumbs.drawButton(minecraft, mouseX, mouseY);
        fun.drawButton(minecraft, mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {modules.setState(false);}
}
