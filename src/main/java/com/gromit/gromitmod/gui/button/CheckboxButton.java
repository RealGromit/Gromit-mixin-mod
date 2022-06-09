package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.RenderUtils;

public class CheckboxButton extends AbstractButton<CheckboxButton> {

    private int alpha;

    public CheckboxButton(int x, int y) {
        super(x, y);
    }

    @Override
    public void drawButton(int mouseX, int mouseY) {
        if (!enabled) return;
        super.drawButton(mouseX, mouseY);

        if (state) {
            RenderUtils.drawLine(x, y, width, 0, 2, true, 255, 255, 255, 255);
            RenderUtils.drawLine(x, y + height, width, 0, 2, true, 255, 255, 255, 255);
            RenderUtils.drawLine(x, y, 0, height, 2, true, 255, 255, 255, 255);
            RenderUtils.drawLine(x + width, y, 0, height, 2, true, 255, 255, 255, 255);
            RenderUtils.drawLine(x, y, width, height, 2, true, 255, 255, 255, alpha);
            RenderUtils.drawLine(x, y + height, width, -height, 2, true, 255, 255, 255, alpha);
            if (alpha != 255) alpha += 5;
        } else {
            RenderUtils.drawLine(x, y, width, 0, 2, true, 255, 255, 255, 255);
            RenderUtils.drawLine(x, y + height, width, 0, 2, true, 255, 255, 255, 255);
            RenderUtils.drawLine(x, y, 0, height, 2, true, 255, 255, 255, 255);
            RenderUtils.drawLine(x + width, y, 0, height, 2, true, 255, 255, 255, 255);
            if (alpha != 0) {
                RenderUtils.drawLine(x, y, width, height, 2, true, 255, 255, 255, alpha);
                RenderUtils.drawLine(x, y + height, width, -height, 2, true, 255, 255, 255, alpha);
                alpha -= 5;
            }
        }
    }
}
