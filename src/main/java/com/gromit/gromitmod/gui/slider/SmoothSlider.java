package com.gromit.gromitmod.gui.slider;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

import java.awt.Color;

public class SmoothSlider extends GuiButton {

    private final int minValue;
    private final int steps;
    private final int maxValue;
    public int currentProgress;
    public int currentValue;
    private final int iterations;
    private double guiScale;
    private boolean dragging;

    public SmoothSlider(int buttonId, int width, int height, String buttonText, int minValue, int maxValue, int iterations) {
        super(buttonId, 0, 0, width, height, buttonText);
        this.minValue = minValue;
        this.maxValue = maxValue;
        steps = width / (maxValue - minValue);
        this.iterations = iterations;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX <= xPosition + width && mouseY < yPosition + height;
        mouseDragged(minecraft, mouseX, mouseY);
        RenderUtils.drawRoundedThinRectangle(xPosition, yPosition, width, height, iterations, Color.WHITE.getRGB());
        RenderUtils.drawCircleFilled(xPosition + currentProgress, yPosition + height / 2.0, 2, 200, ColorUtils.getRGB());
        currentValue = (currentProgress / steps) + minValue;
        displayString = String.valueOf((currentProgress / steps) + minValue);
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        if (hovered) {
            currentProgress = (int) (Math.ceil((float) (mouseX - xPosition) / steps) * steps);
            dragging = true;
            return true;
        } else return false;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (dragging && mouseX >= xPosition && mouseX <= (xPosition + width)) {

            int pos = mouseX - xPosition;
            currentProgress = pos;

        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {dragging = false;}

    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {}

    public void setGuiScale(double guiScale) {this.guiScale = guiScale;}

    public void updateSlider(int x1, int y1, double guiScale) {
        setGuiScale(guiScale);
        xPosition = x1;
        yPosition = y1;
    }
}
