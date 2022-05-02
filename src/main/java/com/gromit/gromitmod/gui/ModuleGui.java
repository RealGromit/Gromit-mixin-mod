package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ScrollableButton;
import com.gromit.gromitmod.handler.buttonhandler.ModuleGuiButtons;
import com.gromit.gromitmod.utils.RenderUtils;
import org.lwjgl.input.Mouse;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class ModuleGui extends MainGui {

    private int scroll;

    protected final ScrollableButton rangeCalc;

    public ModuleGui(GromitMod gromitMod) {
        super(gromitMod);
        rangeCalc = ModuleGuiButtons.getRangeCalc();
    }

    @Override
    public void initGui() {
        super.initGui();

        scroll = 0;
        updateTextButton(rangeCalc, mainGuiPointX + 11, mainGuiPointY + 39);
        buttonList.add(rangeCalc);
        modules.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        glEnable(GL_SCISSOR_TEST);
        glScissor(460, 290, 199, 398);
        rangeCalc.setScrollOffset(scroll);
        rangeCalc.drawButton(minecraft, mouseX, mouseY);
        glDisable(GL_SCISSOR_TEST);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 60, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 138, 50, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(mainGuiPointX + 10, mainGuiPointY + 37, 0, 101, 2, 255, 255, 255, 255);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        if (Mouse.getX() >= 460 && Mouse.getY() >= 290 && Mouse.getX() < 460 + 199 && Mouse.getY() < 290 + 398) scroll += Mouse.getEventDWheel() / 60;
    }

    @Override
    public void onGuiClosed() {modules.setState(false);}
}
