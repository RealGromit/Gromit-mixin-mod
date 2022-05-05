package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class KeyBindButton extends GuiButton {

    private double guiScale;
    private boolean detectingKeybind = false;

    public KeyBindButton(int buttonId, int x, int y, int width, int height, String displayString) {
        super(buttonId, x, y, width, height, displayString);
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
        if (enabled && visible && !detectingKeybind && mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            displayString = "...";
            width = (int) (FontUtil.normal.getStringWidth(displayString) / 2);
            detectingKeybind = true;
            return true;
        }
        return false;
    }



    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {}

    public void setGuiScale(double guiScale) {
        this.guiScale = guiScale;
    }

    public void setDetectingKeybind(boolean detectingKeybind) {
        this.detectingKeybind = detectingKeybind;
    }

    public boolean isDetectingKeybind() {
        return detectingKeybind;
    }

    public void updateButton(int x1, int y1, double guiScale) {
        setGuiScale(guiScale);
        this.xPosition = x1;
        this.yPosition = y1;
    }
}
