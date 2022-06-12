package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.MathHelper;

import java.awt.Color;

public class ColorButton extends AbstractButton<ColorButton> {

    @Getter protected float boxX;
    @Getter protected float boxY;
    @Getter protected float boxWidth, boxHeight, sectionX, sectionY, deltaSectionX, deltaSectionY;
    @Getter protected int red = 255, green, blue, alpha = 255, satRed = 255, satGreen, satBlue;
    @Getter @Setter protected transient boolean hueHovered;
    @Getter @Setter protected transient boolean saturationHovered;
    @Getter @Setter protected transient boolean alphaHovered;
    @Getter @Setter protected transient boolean hueDragging;
    @Getter @Setter protected transient boolean saturationDragging;
    @Getter @Setter protected transient boolean alphaDragging;
    @Getter protected float hue = 1, saturation = 1, brightness = 1;

    @Getter private final ToggleButton chroma;

    public ColorButton(float x, float y, float boxX, float boxY, float boxWidth, float boxHeight) {
        super(x, y);
        this.boxX = boxX;
        this.boxY = boxY;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        sectionX = (int) (boxWidth * 0.8);
        sectionY = (int) (boxHeight * 0.8);
        deltaSectionX = boxWidth - sectionX;
        deltaSectionY = boxHeight - sectionY;
        chroma = new ToggleButton(boxX + FontManager.getNormalSize().getWidth("Chroma rgb") + 7, boxY + boxHeight + 3.8f);
    }

    @Override
    public void drawButton(float mouseX, float mouseY) {
        if (!enabled) return;
        super.drawButton(mouseX, mouseY);

        RenderUtils.drawRectangle(x, y, width, height, ColorUtils.RGBA2Integer(red, green, blue, alpha));
        RenderUtils.drawLine(x, y, width, 0, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(x, y + height, width, 0, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(x, y, 0, height, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(x + width, y, 0, height, 2, true, 255, 255, 255, 255);
        drawColorPicker(mouseX, mouseY);
    }

    private void drawColorPicker(float mouseX, float mouseY) {
        if (!state) return;
        saturationHovered = mouseX >= boxX && mouseY >= boxY && mouseX <= boxX + sectionX - 1.4 && mouseY <= boxY + sectionY - 1.4;
        hueHovered = mouseX >= boxX + sectionX && mouseY >= boxY && mouseX < boxX + sectionX + deltaSectionX - 1 && mouseY < boxY + sectionY - 1;
        alphaHovered = mouseX >= boxX && mouseY >= boxY + sectionY && mouseX < boxX + boxWidth - 1 && mouseY < boxY + sectionY + deltaSectionY - 1;
        if (chroma.isState()) updateRGB(Color.RGBtoHSB(ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), null));
        RenderUtils.drawLine(boxX, boxY, boxWidth, 0, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY + boxHeight, boxWidth, 0, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY, 0, boxHeight, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX + boxWidth, boxY, 0, boxHeight, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX + sectionX, boxY, 0, sectionY, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY + sectionY, boxWidth, 0, 2, true, 255, 255, 255, 255);

        float hsbSection = sectionY / 7f;
        RenderUtils.drawShadingRectangle(boxX + 0.3f, boxY + 0.3f, sectionX - 0.6f, sectionY - 0.6f, satRed, satGreen, satBlue, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3f, boxY + 0.3f, deltaSectionX - 0.6f, hsbSection - 0.1f, 255, 0, 0, 255, 255, 165, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3f, boxY + 0.3f + hsbSection - 0.1f, deltaSectionX - 0.6f, hsbSection - 0.1f, 255, 165, 0, 255, 255, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3f, boxY + 0.3f + hsbSection * 2 - 0.2f, deltaSectionX - 0.6f, hsbSection - 0.1f, 255, 255, 0, 255, 0, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3f, boxY + 0.3f + hsbSection * 3 - 0.3f, deltaSectionX - 0.6f, hsbSection - 0.1f, 0, 255, 0, 255, 0, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3f, boxY + 0.3f + hsbSection * 4 - 0.4f, deltaSectionX - 0.6f, hsbSection - 0.1f, 0, 0, 255, 255, 255, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3f, boxY + 0.3f + hsbSection * 5 - 0.5f, deltaSectionX - 0.6f, hsbSection - 0.1f, 255, 0, 255, 255, 255, 20, 147, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3f, boxY + 0.3f + hsbSection * 6 - 0.6f, deltaSectionX - 0.6f, hsbSection, 255, 20, 147, 255, 255, 0, 0, 255);
        RenderUtils.drawShadingRectangleHeightGradient(boxX + 0.3f, boxY + sectionY + 0.3f, boxWidth - 0.6f, deltaSectionY - 0.6f, 0, 0, 0, 0, red, green, blue, 255);
        FontManager.getNormalSize().drawString("Chroma rgb", boxX + 1, boxY + boxHeight + 3, Color.WHITE.getRGB());
        chroma.drawButton(mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(float mouseButton, float mouseX, float mouseY) {
        super.mousePressed(mouseButton, mouseX, mouseY);

        if (hueHovered) {
            setHue(mouseY);
            hueDragging = true;
            chroma.setState(false);
            return true;
        }
        if (saturationHovered) {
            setSaturation(mouseX, mouseY);
            saturationDragging = true;
            chroma.setState(false);
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
    public void mouseDragged(float mouseX, float mouseY) {
        super.mouseDragged(mouseX, mouseY);

        if (hueDragging && hueHovered) setHue(mouseY);
        if (saturationDragging && saturationHovered) setSaturation(mouseX, mouseY);
        if (alphaDragging && alphaHovered) setAlpha(mouseX);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY) {
        super.mouseReleased(mouseX, mouseY);

        hueDragging = false;
        saturationDragging = false;
        alphaDragging = false;
    }

    private void setHue(float mouseY) {
        hue = MathHelper.clamp_float((mouseY - boxY) / (sectionY - 2f), 0, 1);
        int color = Color.HSBtoRGB(hue, 1, 1);
        satRed = color >> 16 & 255;
        satGreen = color >> 8 & 255;
        satBlue = color & 255;
        updateColor();
    }

    private void setSaturation(float mouseX, float mouseY) {
        float sectionX = sectionY - 2f;
        saturation = MathHelper.clamp_float((mouseX - boxX) / sectionX, 0, 1);
        brightness = MathHelper.clamp_float((boxY - mouseY + sectionX) / sectionX, 0, 1);
        updateColor();
    }

    private void setAlpha(float mouseX) {
        alpha = (int) (MathHelper.clamp_float((mouseX - boxX) / (boxWidth - 2f), 0, 1) * 255);
        updateColor();
    }

    private void updateColor() {
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        red = color >> 16 & 255;
        green = color >> 8 & 255;
        blue = color & 255;
    }

    public void updateRGB(float[] hsb) {
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

    public ColorButton setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
        return this;
    }

    public ColorButton setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
        return this;
    }
}
