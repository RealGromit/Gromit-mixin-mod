package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class CheckboxButton extends AbstractBaseButton {

    private int alpha;

    public CheckboxButton(int buttonId, int x, int y, int width, int height, OnEnable onEnable, OnDisable onDisable) {
        super(buttonId, x, y, width, height, "", onEnable, onDisable);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        if (state) {
            RenderUtils.drawLine(xPosition, yPosition, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition + height, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition, 0, height, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition + width, yPosition, 0, height, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition, width, height, 2, 255, 255, 255, alpha);
            RenderUtils.drawLine(xPosition, yPosition + height, width, -height, 2, 255, 255, 255, alpha);
            if (alpha != 255) alpha += 5;
        } else {
            RenderUtils.drawLine(xPosition, yPosition, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition + height, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition, 0, height, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition + width, yPosition, 0, height, 2, 255, 255, 255, 255);
            if (alpha != 0) {
                RenderUtils.drawLine(xPosition, yPosition, width, height, 2, 255, 255, 255, alpha);
                RenderUtils.drawLine(xPosition, yPosition + height, width, -height, 2, 255, 255, 255, alpha);
                alpha -= 5;
            }
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (hovered) {
            if (state) onDisable.onDisable(this);
            else onEnable.onEnable(this);
            state = !state;
            return true;
        } return false;
    }
}
