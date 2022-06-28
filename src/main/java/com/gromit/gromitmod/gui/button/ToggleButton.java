package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class ToggleButton extends AbstractButton<ToggleButton> {

    private final int rectangleX;
    private final int rectangleY;
    private int red = 255, green;
    private transient boolean moving;

    public ToggleButton(int x, int y) {
        super(x - 8, y - 2);
        width = 16;
        height = 16;
        rectangleX = x;
        rectangleY = y;
    }

    @Override
    public void drawButton(int mouseX, int mouseY) {
        super.drawButton(mouseX, mouseY);

        if (state && moving) {
            x += 100f / Minecraft.getDebugFPS();
            if (x >= rectangleX + 14) {
                x = rectangleX + 14;
                red = 0;
                green = 255;
                moving = false;
            }
        } else if (!state && moving) {
            x -= 100f / Minecraft.getDebugFPS();
            if (x <= rectangleX - 8) {
                x = rectangleX - 8;
                red = 255;
                green = 0;
                moving = false;
            }
        }
        RenderUtils.drawRoundedRectangleOutline(rectangleX, rectangleY, 20, 12, 6, 1, 1, Color.WHITE.getRGB());
        RenderUtils.drawCircleFilledOutline(x, y, 8, 1.3f, 1.6f, ColorUtils.RGB2Integer(red, green, 0), Color.WHITE.getRGB());
    }

    @Override
    public void setState(boolean state) {
        super.setState(state);

        moving = true;
    }
}
