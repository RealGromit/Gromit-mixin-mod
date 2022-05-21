package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;

import java.util.function.Consumer;

public class CheckboxButton extends AbstractBaseButton {

    private int alpha;

    public CheckboxButton(int buttonId, int width, int height, Consumer<AbstractBaseButton> onEnable, Consumer<AbstractBaseButton> onDisable) {
        super(buttonId, width, height, "", onEnable, onDisable);
    }

    public CheckboxButton(int buttonId, int width, int height) {
        super(buttonId, width, height, "");
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
            if (state && onDisable != null) onDisable.accept(this);
            else if (!state && onEnable != null) onEnable.accept(this);
            state = !state;
            return true;
        } return false;
    }
}
