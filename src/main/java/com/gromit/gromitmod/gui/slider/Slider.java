package com.gromit.gromitmod.gui.slider;

import com.gromit.gromitmod.gui.button.AbstractButton;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;

import java.awt.Color;

public class Slider extends AbstractButton<Slider> {

    private int minValue;
    private int steps;
    private int currentProgress;
    public int currentValue;
    private int iterations;
    private boolean dragging;
    public String buttonName;

    public Slider(int x, int y) {
        super(x, y);
    }

    @Override
    public void drawButton(int mouseX, int mouseY) {
        super.drawButton(mouseX, mouseY);

        RenderUtils.drawRoundedThinRectangle(x, y, width, height, iterations, Color.WHITE.getRGB());
        RenderUtils.drawCircleFilled(x + currentProgress, y + height / 2.0, 2, 200, ColorUtils.getRGB());
        currentValue = (currentProgress / steps) + minValue;
        buttonName = String.valueOf((currentProgress / steps) + minValue);
    }

    @Override
    public boolean mousePressed(int mouseButton, int mouseX, int mouseY) {
        super.mousePressed(mouseButton, mouseX, mouseY);

        if (hovering) {
            currentProgress = Math.round((float) (mouseX - x) / steps) * steps;
            dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY) {
        super.mouseDragged(mouseX, mouseY);

        if (dragging && mouseX >= x && mouseX <= (x + width)) {
            int pos = mouseX - x;
            if (pos == 0) currentProgress = 0;
            if (pos % steps == 0) currentProgress = pos;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);

        dragging = false;
    }

    public Slider setSteps(int minValue, int maxValue) {
        this.minValue = minValue;
        steps = width / (maxValue - minValue);
        return this;
    }

    public Slider setIterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    public Slider setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        return this;
    }
}
