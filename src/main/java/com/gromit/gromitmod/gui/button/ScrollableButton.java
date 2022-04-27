package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ScrollableButton extends TextButton {

    private int scroll;

    public ScrollableButton(int buttonId, int x, int y, int width, int height, String buttonText, double guiScale, OnMousePress onMousePress) {
        super(buttonId, x, y, width, height, buttonText, guiScale, onMousePress);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (enabled) {
            mouseX /= guiScale;
            mouseY /= guiScale;
            hovered = mouseX >= xPosition && mouseY >= yPosition - scroll && mouseX < xPosition + width && mouseY < yPosition - scroll + height;
            if (hovered) {
                FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
            } else if (state) {
                FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
            } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, Color.WHITE.getRGB());
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        if (enabled && visible && mouseX >= xPosition && mouseY >= yPosition - scroll && mouseX < xPosition + width && mouseY < yPosition - scroll + height) {
            onMousePress.onMousePress();
            return true;
        }
        return false;
    }

    public void setScrollOffset(int scroll) {
        this.scroll = scroll;
    }
}
