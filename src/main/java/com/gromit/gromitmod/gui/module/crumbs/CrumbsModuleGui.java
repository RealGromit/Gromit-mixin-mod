package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.utils.RenderUtils;
import org.lwjgl.input.Mouse;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class CrumbsModuleGui extends MainGui {

    private int scroll;

    protected static final TextButton explosionBox = new TextButton(6, 0, 0, 4, "Explosion Box",
            () -> minecraft.displayGuiScreen(ExplosionBoxGui.getInstance()),
            () -> minecraft.displayGuiScreen(CrumbsModuleGui.getInstance()));

    public CrumbsModuleGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        explosionBox.updateButton(mainGuiPointX + 11, mainGuiPointY + 38, guiScale);
        buttonList.add(explosionBox);
        crumbs.setState(true);
        fps.setState(false);
        fun.setState(false);
        render.setState(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        glEnable(GL_SCISSOR_TEST);
        glScissor(460, 290, 199, 398);
        explosionBox.drawButton(minecraft, mouseX, mouseY);
        glDisable(GL_SCISSOR_TEST);

        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 138, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
    }

    @Override
    public void onGuiClosed() {
        crumbs.setState(false);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        if (Mouse.getX() >= 460 && Mouse.getY() >= 290 && Mouse.getX() < 460 + 199 && Mouse.getY() < 290 + 398) scroll += Mouse.getEventDWheel() / 60;
    }
}
