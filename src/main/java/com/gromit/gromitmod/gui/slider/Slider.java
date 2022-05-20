package com.gromit.gromitmod.gui.slider;

import com.gromit.gromitmod.saver.PersistSlider;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

import java.awt.Color;

public class Slider extends GuiButton {

    private final int minValue;
    private final int steps;
    public int currentValue;
    private final int iterations;
    private double guiScale;
    private boolean dragging;
    private final PersistSlider persistSlider;

    public Slider(int buttonId, int x, int y, int width, int height, String buttonText, int minValue, int maxValue, int iterations, PersistSlider persistSlider) {
        super(buttonId, x, y, width, height, buttonText);
        this.minValue = minValue;
        steps = width / (maxValue - minValue);
        this.iterations = iterations;
        this.persistSlider = persistSlider;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX <= xPosition + width && mouseY < yPosition + height;
        mouseDragged(minecraft, mouseX, mouseY);
        RenderUtils.drawRoundedThinRectangle(xPosition, yPosition, width, height, iterations, Color.WHITE.getRGB());
        RenderUtils.drawCircleFilled(xPosition + persistSlider.getCurrentProgress(), yPosition + height / 2.0, 2, 200, ColorUtils.getRGB());
        currentValue = (persistSlider.getCurrentProgress() / steps) + minValue;
        displayString = String.valueOf((persistSlider.getCurrentProgress() / steps) + minValue);
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        if (hovered) {
            persistSlider.setCurrentProgress(Math.round((float) (mouseX - xPosition) / steps) * steps);
            dragging = true;
            return true;
        } else return false;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (dragging && hovered) {
            int pos = mouseX - xPosition;
            if (pos == 0) persistSlider.setCurrentProgress(0);
            if (pos % steps == 0) persistSlider.setCurrentProgress(pos);
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
