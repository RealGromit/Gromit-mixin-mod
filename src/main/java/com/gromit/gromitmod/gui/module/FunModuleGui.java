package com.gromit.gromitmod.gui.module;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.RenderUtils;
import org.lwjgl.input.Mouse;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class FunModuleGui extends MainGui {

    private static FunModuleGui instance;
    private int scroll;

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
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        glEnable(GL_SCISSOR_TEST);
        glScissor(460, 290, 199, 398);
        glDisable(GL_SCISSOR_TEST);

        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 138, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        if (Mouse.getX() >= 460 && Mouse.getY() >= 290 && Mouse.getX() < 460 + 199 && Mouse.getY() < 290 + 398) scroll += Mouse.getEventDWheel() / 60;
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static FunModuleGui getInstance() {
        return instance;
    }
}
