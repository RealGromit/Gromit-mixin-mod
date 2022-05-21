package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.gui.button.ColorButton;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorUtils {

    private static int red = 255, green = 0, blue = 0;
    public static final List<ColorButton> colorButtons = new ArrayList<>();

    public static void refreshColors() {
        if (red == 255 && green < 255 && blue == 0) green++;
        if (red > 0 && green == 255 && blue == 0) red--;
        if (red == 0 && green == 255 && blue < 255) blue++;
        if (red == 0 && green > 0 && blue == 255) green--;
        if (red < 255 && green == 0 && blue == 255) red++;
        if (red == 255 && green == 0 && blue > 0) blue--;
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        for (ColorButton colorButton : colorButtons) {
            if (colorButton.getChroma().isState()) colorButton.updateRGB(hsb);
        }
    }

    public static int getRed() {
        return red;
    }

    public static int getGreen() {
        return green;
    }

    public static int getBlue() {
        return blue;
    }

    public static int getRGB(int opacity) {
        return ((opacity & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8)  | ((blue & 0xFF));
    }

    public static int getRGB() {
        return ((0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8)  | ((blue & 0xFF));
    }
}
