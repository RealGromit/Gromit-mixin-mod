package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.MathHelper;

import java.awt.Color;

public class ColorButton extends AbstractButton<ColorButton> {

    @Getter protected int boxX;
    @Getter protected int boxY;
    @Getter protected int boxWidth, boxHeight, sectionX, sectionY, deltaSectionX, deltaSectionY;
    @Getter protected int red = 255, green, blue, alpha = 255, satRed = 255, satGreen, satBlue;
    @Getter @Setter protected transient boolean hueHovered;
    @Getter @Setter protected transient boolean saturationHovered;
    @Getter @Setter protected transient boolean alphaHovered;
    @Getter @Setter protected transient boolean hueDragging;
    @Getter @Setter protected transient boolean saturationDragging;
    @Getter @Setter protected transient boolean alphaDragging;
    @Getter protected float hue = 1, saturation = 1, brightness = 1;

    @Getter private final ToggleButton chroma;

    public ColorButton(int x, int y, int boxX, int boxY, int boxWidth, int boxHeight) {
        super(x, y);
        this.boxX = boxX;
        this.boxY = boxY;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        sectionX = (int) (boxWidth * 0.8);
        sectionY = (int) (boxHeight * 0.8);
        deltaSectionX = boxWidth - sectionX;
        deltaSectionY = boxHeight - sectionY;
        chroma = new ToggleButton(boxX + normal.getWidth("Chroma rgb") + 16, boxY + boxHeight + 14);
    }

    @Override
    public void drawButton(int mouseX, int mouseY) {
        if (!enabled) return;
        super.drawButton(mouseX, mouseY);

        RenderUtils.drawRectangle(x, y, width, height, ColorUtils.RGBA2Integer(red, green, blue, alpha));
        RenderUtils.drawRoundedRectangleOutline(x, y, width, height, 3, 1, 1, Color.WHITE.getRGB(), false);
        drawColorPicker(mouseX, mouseY);
    }

    private void drawColorPicker(int mouseX, int mouseY) {
        if (!state) return;
        saturationHovered = mouseX >= boxX && mouseY >= boxY && mouseX < boxX + sectionX && mouseY < boxY + sectionY;
        hueHovered = mouseX >= boxX + sectionX && mouseY >= boxY && mouseX < boxX + boxWidth && mouseY < boxY + sectionY;
        alphaHovered = mouseX >= boxX && mouseY >= boxY + sectionY && mouseX < boxX + boxWidth && mouseY < boxY + boxHeight;
        if (chroma.isState()) updateRGB(Color.RGBtoHSB(ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), null));

        float hsbSection = sectionY / 7f - 0.3f;
        RenderUtils.drawShadingRectangle(boxX + 1, boxY + 1, sectionX - 2, sectionY - 2, satRed, satGreen, satBlue, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 1, boxY + 1, deltaSectionX - 2, hsbSection, 255, 0, 0, 255, 255, 165, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 1, boxY + 1 + hsbSection, deltaSectionX - 2, hsbSection, 255, 165, 0, 255, 255, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 1, boxY + 1 + hsbSection * 2, deltaSectionX - 2, hsbSection, 255, 255, 0, 255, 0, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 1, boxY + 1 + hsbSection * 3, deltaSectionX - 2, hsbSection, 0, 255, 0, 255, 0, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 1, boxY + 1 + hsbSection * 4, deltaSectionX - 2, hsbSection, 0, 0, 255, 255, 255, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 1, boxY + 1 + hsbSection * 5, deltaSectionX - 2, hsbSection, 255, 0, 255, 255, 255, 20, 147, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 1, boxY + 1 + hsbSection * 6, deltaSectionX - 2, hsbSection, 255, 20, 147, 255, 255, 0, 0, 255);
        RenderUtils.drawShadingRectangleHeightGradient(boxX + 1, boxY + sectionY + 1, boxWidth - 2, deltaSectionY - 2, 0, 0, 0, 0, red, green, blue, 255);
        RenderUtils.drawRoundedRectangleOutline(boxX, boxY, boxWidth, boxHeight, 4, 2, 1, Color.WHITE.getRGB(), false);
        RenderUtils.drawLine(boxX + sectionX, boxY, 0, sectionY, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(boxX, boxY + sectionY, boxWidth, 0, 2, true, 255, 255, 255, 255);
        normal.drawString("Chroma rgb", boxX + 4, boxY + boxHeight + 12, Color.WHITE.getRGB());
        chroma.drawButton(mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(int mouseButton, int mouseX, int mouseY) {
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
        hue = MathHelper.clamp_float((mouseY - boxY) / sectionY, 0, 1);
        int color = Color.HSBtoRGB(hue, 1, 1);
        satRed = color >> 16 & 255;
        satGreen = color >> 8 & 255;
        satBlue = color & 255;
        updateColor();
    }

    private void setSaturation(float mouseX, float mouseY) {
        saturation = MathHelper.clamp_float((mouseX - boxX) / sectionX, 0, 1);
        brightness = MathHelper.clamp_float((boxY - mouseY + sectionX) / sectionX, 0, 1);
        updateColor();
    }

    private void setAlpha(float mouseX) {
        alpha = (int) (MathHelper.clamp_float((mouseX - boxX) / boxWidth, 0, 1) * 255);
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

    public int getColor() {
        return ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF));
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
