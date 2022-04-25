package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class TextButton extends GuiButton {

    private final double guiScale;
    private final OnMousePress onMousePress;

    public TextButton(int buttonId, int x, int y, int width, int height, String buttonText, double guiScale, OnMousePress onMousePress) {
        super(buttonId, x, y, width, height, buttonText);
        this.guiScale = guiScale;
        this.onMousePress = onMousePress;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (enabled) {
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            if (hovered) {
                FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, ColorUtils.getRGB());
            } else {
                FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, Color.WHITE.getRGB());
            }
        }
    }

    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {}

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        if (enabled && visible && mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            onMousePress.onMousePress();
            return true;
        }
        return false;
    }

    public interface OnMousePress {
        void onMousePress();
    }
}
