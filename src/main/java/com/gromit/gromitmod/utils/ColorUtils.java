package com.gromit.gromitmod.utils;

public class ColorUtils {

    private static int red = 255, green = 0, blue = 0;

    public static void colorTask() {
        refreshColors();
    }

    private static void refreshColors() {
        if (red == 255 && green < 255 && blue == 0) green++;
        if (red > 0 && green == 255 && blue == 0) red--;
        if (red == 0 && green == 255 && blue < 255) blue++;
        if (red == 0 && green > 0 && blue == 255) green--;
        if (red < 255 && green == 0 && blue == 255) red++;
        if (red == 255 && green == 0 && blue > 0) blue--;
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
}
