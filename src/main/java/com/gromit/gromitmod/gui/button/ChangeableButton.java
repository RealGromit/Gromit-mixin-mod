package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ChangeableButton extends TextButton {

    private final OnDisable onDisable;
    private final String disabledState;
    private final String enabledState;

    public ChangeableButton(int buttonId, int x, int y, int width, int height, String disabledState, String enabledState, double guiScale, OnEnable onEnable, OnDisable onDisable) {
        super(buttonId, x, y, width, height, disabledState, guiScale, onEnable);
        this.onDisable = onDisable;
        this.disabledState = disabledState;
        this.enabledState = enabledState;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (visible) {
            mouseX /= guiScale;
            mouseY /= guiScale;
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            if (hovered) {
                FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, ColorUtils.getRGB());
            } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, Color.WHITE.getRGB());
        }
    }


    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        if (enabled && visible && mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            if (state) {
                onDisable.onDisable();
                displayString = disabledState;
                width = (int) (FontUtil.normal.getStringWidth(displayString) / 2);
                setState(false);
            } else {
                onEnable.onEnable();
                displayString = enabledState;
                width = (int) (FontUtil.normal.getStringWidth(displayString) / 2);
                setState(true);
            }
            return true;
        }
        return false;
    }

    public interface OnDisable {
        void onDisable();
    }
}
