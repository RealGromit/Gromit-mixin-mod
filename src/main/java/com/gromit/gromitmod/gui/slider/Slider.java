package com.gromit.gromitmod.gui.slider;

import com.gromit.gromitmod.gui.button.AbstractButton;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class Slider extends AbstractButton<Slider> {

    private float minValue;
    private float steps;
    private float currentProgress;
    public float currentValue;
    private transient boolean dragging;
    private boolean floor;
    private transient boolean moving;
    private transient float delta;
    private transient float neededCurrentProgress;
    public String buttonName;

    public Slider(int x, int y, boolean floor) {
        super(x, y);
        this.floor = floor;
    }

    @Override
    public void drawButton(int mouseX, int mouseY) {
        super.drawButton(mouseX, mouseY);

        RenderUtils.drawRoundedRectangle(x, y, width, height, 0.9f, height / 2f, Color.WHITE.getRGB());
        RenderUtils.drawRoundedRectangle(x + currentProgress, y, 24, height, 0.9f, height / 2f, ColorUtils.getRGB());
        if (moving) {
            currentProgress += delta / Minecraft.getDebugFPS() * 10;
            if (delta < 0 && currentProgress <= neededCurrentProgress) {
                currentProgress = neededCurrentProgress;
                moving = false;
            } else if (delta > 0 && currentProgress >= neededCurrentProgress) {
                currentProgress = neededCurrentProgress;
                moving = false;
            }
        }
        else if (floor) buttonName = String.valueOf(Math.floor(currentProgress / steps) + minValue);
        else buttonName = String.valueOf(currentProgress / steps + minValue);

        currentValue = currentProgress / steps + minValue;
    }

    @Override
    public boolean mousePressed(int mouseButton, int mouseX, int mouseY) {
        super.mousePressed(mouseButton, mouseX, mouseY);

        if (isMouseOver(mouseX, mouseY)) {
            currentProgress = mouseX - x - 12;
            dragging = true;
            return true;
        }
        if (mouseX >= x && mouseY >= y && mouseX < x + 12 && mouseY < y + height) {
            currentProgress = 0;
            dragging = true;
            return true;
        }
        if (mouseX >= x + width - 12 && mouseY >= y && mouseX < x + width && mouseY < y + height) {
            currentProgress = width - 24;
            dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseDragged(float mouseX, float mouseY) {
        super.mouseDragged(mouseX, mouseY);

        if (dragging && mouseX >= x + 12 && mouseX <= (x + width - 12)) {
            currentProgress = mouseX - x - 12;
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY) {
        super.mouseReleased(mouseX, mouseY);

        if (floor) {
            int roundedValue = Math.round(currentValue);
            delta = roundedValue * steps - steps - currentProgress;
            neededCurrentProgress = roundedValue * steps - steps;
            moving = true;
        }
        dragging = false;
    }

    @Override
    protected boolean isMouseOver(float mouseX, float mouseY) {
        return mouseX >= x + 12 && mouseY >= y && mouseX < x + width - 12 && mouseY < y + height;
    }

    @Override
    public Slider setWidth(int width) {
        this.width = width + 24;
        return this;
    }

    public Slider setMinMax(int minValue, int maxValue) {
        this.minValue = minValue;
        steps = (width - 24f) / (maxValue - minValue);
        return this;
    }

    public Slider setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        return this;
    }
}
