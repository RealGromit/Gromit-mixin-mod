package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.MathHelper;

import java.awt.Color;

public class ColorButton extends AbstractButton<ColorButton> {

    @Getter protected int boxX;
    @Getter protected int boxY;
    @Getter protected int boxWidth, boxHeight, sectionX, sectionY, deltaSectionX, deltaSectionY;
    @Getter protected int red = 255, green, blue, alpha = 255, satRed = 255, satGreen, satBlue;
    @Getter @Setter protected boolean hueHovered;
    @Getter @Setter protected boolean saturationHovered;
    @Getter @Setter protected boolean alphaHovered;
    @Getter @Setter protected boolean hueDragging;
    @Getter @Setter protected boolean saturationDragging;
    @Getter @Setter protected boolean alphaDragging;
    @Getter protected float hue = 1, saturation = 1, brightness = 1;

    @Getter private final CheckboxButton chroma;

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
        chroma = new CheckboxButton((int) (boxX + FontUtil.normal.getStringWidth("Chroma rgb") / 2) + 3, boxY + boxHeight + 3)
                .setWidth(4)
                .setHeight(4);
    }

    @Override
    public void drawButton(int mouseX, int mouseY) {
        if (!enabled) return;
        super.drawButton(mouseX, mouseY);

        RenderUtils.drawRectangle(x, y, width, height, red, green, blue, alpha);
        RenderUtils.drawLine(x, y, width, 0, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(x, y + height, width, 0, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(x, y, 0, height, 2, true, 255, 255, 255, 255);
        RenderUtils.drawLine(x + width, y, 0, height, 2, true, 255, 255, 255, 255);
        drawColorPicker(mouseX, mouseY);
    }

    private void drawColorPicker(int mouseX, int mouseY) {
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
        RenderUtils.drawShadingRectangle(boxX + 0.3, boxY + 0.3, sectionX - 0.6, sectionY - 0.6, satRed, satGreen, satBlue, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3, deltaSectionX - 0.6, hsbSection - 0.1, 255, 0, 0, 255, 255, 165, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + hsbSection - 0.1, deltaSectionX - 0.6, hsbSection - 0.1, 255, 165, 0, 255, 255, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + hsbSection * 2 - 0.2, deltaSectionX - 0.6, hsbSection - 0.1, 255, 255, 0, 255, 0, 255, 0, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + hsbSection * 3 - 0.3, deltaSectionX - 0.6, hsbSection - 0.1, 0, 255, 0, 255, 0, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + hsbSection * 4 - 0.4, deltaSectionX - 0.6, hsbSection - 0.1, 0, 0, 255, 255, 255, 0, 255, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + hsbSection * 5 - 0.5, deltaSectionX - 0.6, hsbSection - 0.1, 255, 0, 255, 255, 255, 20, 147, 255);
        RenderUtils.drawShadingRectangleWidthGradient(boxX + sectionX + 0.3, boxY + 0.3 + hsbSection * 6 - 0.6, deltaSectionX - 0.6, hsbSection, 255, 20, 147, 255, 255, 0, 0, 255);
        RenderUtils.drawShadingRectangleHeightGradient(boxX + 0.3, boxY + sectionY + 0.3, boxWidth - 0.6, deltaSectionY - 0.6, 0, 0, 0, 0, red, green, blue, 255);
        FontUtil.normal.drawString("Chroma rgb", boxX + 1, boxY + boxHeight + 3, Color.WHITE.getRGB());
        chroma.drawButton(mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(int mouseButton, int mouseX, int mouseY) {
        super.mousePressed(mouseButton, mouseX, mouseY);

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
        }
        return false;
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY) {
        super.mouseDragged(mouseX, mouseY);

        if (hueDragging && hueHovered) setHue(mouseY);
        if (saturationDragging && saturationHovered) setSaturation(mouseX, mouseY);
        if (alphaDragging && alphaHovered) setAlpha(mouseX);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);

        hueDragging = false;
        saturationDragging = false;
        alphaDragging = false;
    }

    private void setHue(int mouseY) {
        hue = MathHelper.clamp_float((mouseY - boxY) / (sectionY - 2f), 0, 1);
        int color = Color.HSBtoRGB(hue, 1, 1);
        satRed = color >> 16 & 255;
        satGreen = color >> 8 & 255;
        satBlue = color & 255;
        updateColor();
    }

    private void setSaturation(int mouseX, int mouseY) {
        float sectionX = sectionY - 2f;
        saturation = MathHelper.clamp_float((mouseX - boxX) / sectionX, 0, 1);
        brightness = MathHelper.clamp_float((boxY - mouseY + sectionX) / sectionX, 0, 1);
        updateColor();
    }

    private void setAlpha(int mouseX) {
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
