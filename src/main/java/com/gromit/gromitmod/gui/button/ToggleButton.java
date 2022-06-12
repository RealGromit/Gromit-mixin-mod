package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class ToggleButton extends AbstractButton<ToggleButton> {

    private final float rectangleX;
    private final float rectangleY;
    private int red = 255, green;
    private transient boolean moving;

    public ToggleButton(float x, float y) {
        super(x - 3, y - 1);
        width = 5;
        height = 4;
        rectangleX = x;
        rectangleY = y;
    }

    @Override
    public void drawButton(float mouseX, float mouseY) {
        super.drawButton(mouseX, mouseY);

        RenderUtils.drawRoundedRectangleOutline(rectangleX, rectangleY, 5, 3, 1, 25, 0.15f, Color.WHITE.getRGB(), 0.55f);
        if (state && moving) {
            x += 5f / Minecraft.getDebugFPS() * 5;
            if (x >= rectangleX + 5 - 3) {
                x = rectangleX + 5 - 3;
                red = 0;
                green = 255;
                moving = false;
            }
        } else if (!state && moving) {
            x -= 5f / Minecraft.getDebugFPS() * 5;
            if (x <= rectangleX - 3) {
                x = rectangleX - 3;
                red = 255;
                green = 0;
                moving = false;
            }
        }
        RenderUtils.drawCircleFilled(x + 3, y + 1 + 3 / 2f, 16, Color.WHITE.getRGB());
        RenderUtils.drawCircleFilled(x + 3, y + 1 + 3 / 2f, 12, ColorUtils.RGBA2Integer(red, green, 0, 255));
    }

    @Override
    public void setState(boolean state) {
        super.setState(state);

        moving = true;
    }
}
