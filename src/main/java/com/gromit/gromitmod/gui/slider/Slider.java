package com.gromit.gromitmod.gui.slider;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;

import java.awt.*;

public class Slider {

    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    private final double startValue;
    private final double minValue;
    private final double maxValue;
    private double currentValue;
    private final int iterations;
    public double guiScale;


    public Slider(int xPosition, int yPosition, int width, int height, double startValue, double minValue, double maxValue, int iterations) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.startValue = startValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.iterations = iterations;
    }

    public void drawSlider(int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        RenderUtils.drawRoundedThinRectangle(xPosition, yPosition, width, height, iterations, Color.WHITE.getRGB());
        RenderUtils.drawCircleFilled(mouseX, mouseY, 2, 200, ColorUtils.getRGB());
    }

}
