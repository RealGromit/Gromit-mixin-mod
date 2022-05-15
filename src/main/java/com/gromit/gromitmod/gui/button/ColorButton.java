package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class ColorButton extends AbstractBaseButton {

    private int boxX, boxY;
    private final int boxWidth, boxHeight;
    private int finalRed = 255, finalGreen, finalBlue, finalAlpha = 255;
    private int red = 255, green, blue;
    private boolean hueHovered = false;
    private boolean saturationHovered = false;
    private boolean alphaHovered = false;
    private boolean hueDragging = false;
    private boolean saturationDragging = false;
    private boolean alphaDragging = false;
    private float hue = 1;
    private float saturation = 1;
    private float brightness = 1;

    public ColorButton(int buttonId, int x, int y, int width, int height, int boxX, int boxY, int boxWidth, int boxHeight) {
        super(buttonId, x, y, width, height, "");
        this.boxX = boxX;
        this.boxY = boxY;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= getGuiScale();
        mouseY /= getGuiScale();
        double x = boxWidth * 4.0 / 5;
        double y = boxHeight * 4.0 / 5;
        double deltaX = boxWidth - x;
        double deltaY = boxHeight - y;

        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

        RenderUtils.drawRectangle(xPosition, yPosition, width, height, finalRed, finalGreen, finalBlue, finalAlpha);
        RenderUtils.drawLine(xPosition, yPosition, width, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(xPosition, yPosition + height, width, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(xPosition, yPosition, 0, height, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(xPosition + width, yPosition, 0, height, 2, 255, 255, 255, 255);
        if (!isState()) return;
        mouseDragged(minecraft, mouseX, mouseY);
        saturationHovered = mouseX >= boxX && mouseY >= boxY && mouseX <= boxX + x - 1.4 && mouseY <= boxY + y - 1.4;
        hueHovered = mouseX >= boxX + x && mouseY >= boxY && mouseX < boxX + x + deltaX - 1 && mouseY < boxY + y - 1;
        alphaHovered = mouseX >= boxX && mouseY >= boxY + y && mouseX < boxX + boxWidth - 1 && mouseY < boxY + y + deltaY - 1;
        RenderUtils.drawLine(boxX, boxY, boxWidth, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY + boxHeight, boxWidth, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY, 0, boxHeight, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX + boxWidth, boxY, 0, boxHeight, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX + x, boxY, 0, y, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY + y, boxWidth, 0, 2, 255, 255, 255, 255);

        RenderUtils.drawShadingRectangle(boxX + 0.3, boxY + 0.3, x - 0.6, y - 0.6, red, green, blue, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + x + 0.3, boxY + 0.3, deltaX - 0.6, 6.7, 255, 0, 0, 255, 255, 165, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + x + 0.3, boxY + 0.3 + 6.7, deltaX - 0.6, 6.7, 255, 165, 0, 255, 255, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + x + 0.3, boxY + 0.3 + 13.5, deltaX - 0.6, 6.7, 255, 255, 0, 255, 0, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + x + 0.3, boxY + 0.3 + 20.3, deltaX - 0.6, 6.7, 0, 255, 0, 255, 0, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + x + 0.3, boxY + 0.3 + 27, deltaX - 0.6, 6.7, 0, 0, 255, 255, 255, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + x + 0.3, boxY + 0.3 + 33.8, deltaX - 0.6, 6.7, 255, 0, 255, 255, 255, 20, 147, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + x + 0.3, boxY + 0.3 + 40.5, deltaX - 0.6, 6.9, 255, 20, 147, 255, 255, 0, 0, 255);
        RenderUtils.drawShadingRectangleHeightGradient(boxX + 0.3, boxY + y + 0.3, boxWidth - 0.6, deltaY - 0.6, 0, 0, 0, 0, finalRed, finalGreen, finalBlue, 255);
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        if (hovered) {
            setState(!isState());
            return true;
        }
        if (hueHovered) {
            setHue(mouseY);
            hueDragging = true;
            return true;
        }
        if (saturationHovered) {
            setSaturation(mouseX, mouseY);
            saturationDragging = true;
            return true;
        }
        if (alphaHovered) {
            setAlpha(mouseX);
            alphaDragging = true;
            return true;
        }

        return false;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (hueDragging && hueHovered) setHue(mouseY);
        if (saturationDragging && saturationHovered) setSaturation(mouseX, mouseY);
        if (alphaDragging && alphaHovered) setAlpha(mouseX);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        saturationDragging = false;
        hueDragging = false;
        alphaDragging = false;
    }

    public void updateColorButton(int x, int y, int boxX, int boxY, double guiScale) {
        this.guiScale = guiScale;
        xPosition = x;
        yPosition = y;
        this.boxX = boxX;
        this.boxY = boxY;
    }

    private void setHue(int mouseY) {
        hue = MathHelper.clamp_float((mouseY - boxY + 1) / 48f, 0, 1);
        int color = Color.HSBtoRGB(hue, 1, 1);
        red = color >> 16 & 255;
        green = color >> 8 & 255;
        blue = color & 255;
        updateColor();
    }

    private void setSaturation(int mouseX, int mouseY) {
        saturation = MathHelper.clamp_float((mouseX - boxX + 1) / 48f, 0, 1);
        brightness = MathHelper.clamp_float((boxY - mouseY + 48 - 1) / 48f, 0, 1);
        updateColor();
    }

    private void setAlpha(int mouseX) {
        finalAlpha = (int) (MathHelper.clamp_float((mouseX - boxX + 1) / 60f, 0, 1) * 255);
        updateColor();
    }

    private void updateColor() {
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        finalRed = color >> 16 & 255;
        finalGreen = color >> 8 & 255;
        finalBlue = color & 255;
    }

    public int getFinalRed() {
        return finalRed;
    }

    public int getFinalGreen() {
        return finalGreen;
    }

    public int getFinalBlue() {
        return finalBlue;
    }

    public int getFinalAlpha() {
        return finalAlpha;
    }
}