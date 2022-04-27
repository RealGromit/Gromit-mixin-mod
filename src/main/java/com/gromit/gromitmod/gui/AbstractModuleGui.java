package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ScrollableButton;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Mouse;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public abstract class AbstractModuleGui extends AbstractGui {

    private int scroll;

    public final ScrollableButton rangeCalc;

    public AbstractModuleGui(GromitMod gromitMod) {
        super(gromitMod);
        rangeCalc = new ScrollableButton(2, mainGuiPointX + 11, mainGuiPointY + 39, (int) (FontUtil.normal.getStringWidth("Range Calc") / 1.9), 4, "Range Calc", guiScale, () -> {
            minecraft.displayGuiScreen(gromitMod.getGuiManager().getRangeCalcModuleGui());
        });
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
        for (GuiButton textButton : buttonList) {
            if (!(textButton instanceof ScrollableButton)) continue;
            ((ScrollableButton) textButton).setScrollOffset(scroll);
            textButton.drawButton(minecraft, mouseX, mouseY);

        }
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
    public void onGuiClosed() {
        modules.setState(false);
    }
}
