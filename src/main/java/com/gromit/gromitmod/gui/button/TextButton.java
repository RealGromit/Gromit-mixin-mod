package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class TextButton extends GuiButton {

    protected double guiScale;
    protected final OnEnable onEnable;
    protected boolean state = false;

    public TextButton(int buttonId, int x, int y, int width, int height, String buttonText, double guiScale, OnEnable onEnable) {
        super(buttonId, x, y, width, height, buttonText);
        this.guiScale = guiScale;
        this.onEnable = onEnable;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (visible) {
            mouseX /= guiScale;
            mouseY /= guiScale;
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            if (hovered) {
                FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, ColorUtils.getRGB());
            } else if (isState()) {
                FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, ColorUtils.getRGB());
            } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, Color.WHITE.getRGB());
        }
    }

    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {}

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= getGuiScale();
        mouseY /= getGuiScale();
        if (enabled && visible && mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            onEnable.onEnable();
            return true;
        }
        return false;
    }

    public interface OnEnable {
        void onEnable();
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getGuiScale() {
        return guiScale;
    }

    public void setGuiScale(double guiScale) {
        this.guiScale = guiScale;
    }
}
