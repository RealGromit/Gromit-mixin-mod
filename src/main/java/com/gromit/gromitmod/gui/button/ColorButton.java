package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import java.awt.Color;

public class ColorButton extends AbstractBaseButton {

    private int boxX, boxY;
    private final int boxWidth, boxHeight, sectionX, sectionY, deltaSectionX, deltaSectionY;
    private int red = 255, green, blue, alpha = 255, satRed = 255, satGreen, satBlue;
    private boolean hueHovered = false;
    private boolean saturationHovered = false;
    private boolean alphaHovered = false;
    private boolean hueDragging = false;
    private boolean saturationDragging = false;
    private boolean alphaDragging = false;
    private float hue = 1, saturation = 1, brightness = 1;
    private final CheckboxButton chroma = new CheckboxButton(11, 0, 0, 4, 4);

    public ColorButton(int buttonId, int x, int y, int width, int height, int boxX, int boxY, int boxWidth, int boxHeight) {
        super(buttonId, x, y, width, height, "");
        this.boxX = boxX;
        this.boxY = boxY;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        sectionX = (int) (boxWidth * 0.8);
        sectionY = (int) (boxHeight * 0.8);
        deltaSectionX = boxWidth - sectionX;
        deltaSectionY = boxHeight - sectionY;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        int ogMouseX = mouseX;
        int ogMouseY = mouseY;
        mouseX /= guiScale;
        mouseY /= guiScale;

        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

        RenderUtils.drawRectangleNoBlend(xPosition, yPosition, width, height, red, green, blue, alpha);
        RenderUtils.drawLine(xPosition, yPosition, width, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(xPosition, yPosition + height, width, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(xPosition, yPosition, 0, height, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(xPosition + width, yPosition, 0, height, 2, 255, 255, 255, 255);
        if (chroma.state) {
            float[] hsb = Color.RGBtoHSB(ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), null);
            hue = hsb[0];
            saturation = hsb[1];
            brightness = hsb[2];
            red = ColorUtils.getRed();
            green = ColorUtils.getGreen();
            blue = ColorUtils.getBlue();
            satRed = red;
            satGreen = green;
            satBlue = blue;
        }
        if (!state) return;
        saturationHovered = mouseX >= boxX && mouseY >= boxY && mouseX <= boxX + sectionX - 1.4 && mouseY <= boxY + sectionY - 1.4;
        hueHovered = mouseX >= boxX + sectionX && mouseY >= boxY && mouseX < boxX + sectionX + deltaSectionX - 1 && mouseY < boxY + sectionY - 1;
        alphaHovered = mouseX >= boxX && mouseY >= boxY + sectionY && mouseX < boxX + boxWidth - 1 && mouseY < boxY + sectionY + deltaSectionY - 1;
        mouseDragged(minecraft, mouseX, mouseY);
        RenderUtils.drawLine(boxX, boxY, boxWidth, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY + boxHeight, boxWidth, 0, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY, 0, boxHeight, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX + boxWidth, boxY, 0, boxHeight, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX + sectionX, boxY, 0, sectionY, 2, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY + sectionY, boxWidth, 0, 2, 255, 255, 255, 255);

        RenderUtils.drawShadingRectangle(boxX + 0.3, boxY + 0.3, sectionX - 0.6, sectionY - 0.6, satRed, satGreen, satBlue, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3, deltaSectionX - 0.6, 6.7, 255, 0, 0, 255, 255, 165, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + 6.7, deltaSectionX - 0.6, 6.7, 255, 165, 0, 255, 255, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + 13.5, deltaSectionX - 0.6, 6.7, 255, 255, 0, 255, 0, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + 20.3, deltaSectionX - 0.6, 6.7, 0, 255, 0, 255, 0, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + 27, deltaSectionX - 0.6, 6.7, 0, 0, 255, 255, 255, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + 33.8, deltaSectionX - 0.6, 6.7, 255, 0, 255, 255, 255, 20, 147, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + 40.5, deltaSectionX - 0.6, 6.9, 255, 20, 147, 255, 255, 0, 0, 255);
        RenderUtils.drawShadingRectangleHeightGradient(boxX + 0.3, boxY + sectionY + 0.3, boxWidth - 0.6, deltaSectionY - 0.6, 0, 0, 0, 0, red, green, blue, 255);
        FontUtil.normal.drawString("Chroma rgb", boxX + 1, boxY + boxHeight + 6, Color.WHITE.getRGB());
        chroma.drawButton(minecraft, ogMouseX, ogMouseY);
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        if (hovered) {
            state = !state;
            return true;
        }
        if (hueHovered) {
            setHue(mouseY);
            hueDragging = true;
            chroma.state = false;
            return true;
        }
        if (saturationHovered) {
            setSaturation(mouseX, mouseY);
            saturationDragging = true;
            chroma.state = false;
            return true;
        }
        if (alphaHovered) {
            setAlpha(mouseX);
            alphaDragging = true;
            return true;
        } return false;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (hueDragging && hueHovered) setHue(mouseY);
        if (saturationDragging && saturationHovered) setSaturation(mouseX, mouseY);
        if (alphaDragging && alphaHovered) setAlpha(mouseX);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        hueDragging = false;
        saturationDragging = false;
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
        hue = MathHelper.clamp_float((mouseY - boxY) / 46f, 0, 1);
        int color = Color.HSBtoRGB(hue, 1, 1);
        satRed = color >> 16 & 255;
        satGreen = color >> 8 & 255;
        satBlue = color & 255;
        updateColor();
    }

    private void setSaturation(int mouseX, int mouseY) {
        saturation = MathHelper.clamp_float((mouseX - boxX) / 46f, 0, 1);
        brightness = MathHelper.clamp_float((boxY - mouseY + 46) / 46f, 0, 1);
        updateColor();
    }

    private void setAlpha(int mouseX) {
        alpha = (int) (MathHelper.clamp_float((mouseX - boxX) / 58f, 0, 1) * 255);
        updateColor();
    }

    private void updateColor() {
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        red = color >> 16 & 255;
        green = color >> 8 & 255;
        blue = color & 255;
    }

    public int getFinalRed() {
        return red;
    }

    public int getFinalGreen() {
        return green;
    }

    public int getFinalBlue() {
        return blue;
    }

    public int getFinalAlpha() {
        return alpha;
    }

    public CheckboxButton getChroma() {
        return chroma;
    }
}