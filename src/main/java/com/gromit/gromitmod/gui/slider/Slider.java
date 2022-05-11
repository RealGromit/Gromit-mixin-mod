package com.gromit.gromitmod.gui.slider;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class Slider extends GuiButton {

    private final int minValue;
    private final int maxValue;
    private final int steps;
    private int currentProgress;
    public int currentValue;
    private final int iterations;
    private double guiScale;
    private boolean dragging;

    public Slider(int buttonId, int x, int y, int width, int height, String buttonText, int minValue, int maxValue, int iterations) {
        super(buttonId, x, y, width, height, buttonText);
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

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        if (hovered) {
            closestStep(mouseX - xPosition);
            dragging = true;
            return true;
        } else return false;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (dragging && hovered) {
            int pos = mouseX - xPosition;
            if (pos == 0) currentProgress = pos;
            if (pos % steps == 0) currentProgress = pos;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        dragging = false;
    }

    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {}

    public void setGuiScale(double guiScale) {
        this.guiScale = guiScale;
    }

    public void updateSlider(int x1, int y1, double guiScale) {
        setGuiScale(guiScale);
        xPosition = x1;
        yPosition = y1;
    }

    private void closestStep(int pos) {
        int positiveCount = 0;
        int remainingPossibleValues = width - pos;
        for (int i = 1; i <= remainingPossibleValues; i++) {
            positiveCount++;
            if ((pos + i) % steps == 0) break;
        }
        int negativeCount = 0;
        remainingPossibleValues = -width + remainingPossibleValues;
        for (int i = -1; i >= remainingPossibleValues; i--) {
            negativeCount--;
            if ((pos + i) % steps == 0) break;
        }
        if (positiveCount <= Math.abs(negativeCount)) currentProgress = pos + positiveCount;
        if (positiveCount >= Math.abs(negativeCount)) currentProgress = pos + negativeCount;
    }
}
