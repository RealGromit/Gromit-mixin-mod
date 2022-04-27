package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ScrollableButton extends TextButton {

    private int scroll;

    public ScrollableButton(int buttonId, int x, int y, int width, int height, String buttonText, double guiScale, OnEnable onEnable) {
        super(buttonId, x, y, width, height, buttonText, guiScale, onEnable);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (visible) {
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
            onEnable.onEnable();
            return true;
        }
        return false;
    }

    public void setScrollOffset(int scroll) {
        this.scroll = scroll;
    }
}
