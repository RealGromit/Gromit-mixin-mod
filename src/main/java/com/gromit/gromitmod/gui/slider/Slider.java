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
    public void drawButton(float mouseX, float mouseY) {
        super.drawButton(mouseX, mouseY);

        RenderUtils.drawRoundedRectangle(x, y, width, height, Color.WHITE.getRGB(), 1);
        RenderUtils.drawRoundedRectangle(x + currentProgress, y, 6, height, ColorUtils.getRGB(), 1);
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
    public boolean mousePressed(float mouseButton, float mouseX, float mouseY) {
        super.mousePressed(mouseButton, mouseX, mouseY);

        if (isMouseOver(mouseX, mouseY)) {
            currentProgress = mouseX - x - 3;
            dragging = true;
            return true;
        }
        if (mouseX >= x && mouseY >= y && mouseX < x + 3 && mouseY < y + height) {
            currentProgress = 0;
            dragging = true;
            return true;
        }
        if (mouseX >= x + width - 3 && mouseY >= y && mouseX < x + width && mouseY < y + height) {
            currentProgress = width - 6;
            dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseDragged(float mouseX, float mouseY) {
        super.mouseDragged(mouseX, mouseY);

        if (dragging && mouseX >= x + 3 && mouseX <= (x + width - 3)) {
            currentProgress = mouseX - x - 3;
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
        return mouseX >= x + 3 && mouseY >= y && mouseX < x + width - 3 && mouseY < y + height;
    }

    @Override
    public Slider setWidth(float width) {
        this.width = width + 6;
        return this;
    }

    public Slider setMinMax(int minValue, int maxValue) {
        this.minValue = minValue;
        steps = (width - 6) / (maxValue - minValue);
        return this;
    }

    public Slider setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        return this;
    }
}
