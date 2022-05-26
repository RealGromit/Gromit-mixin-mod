package com.gromit.gromitmod.utils;

public class MathUtils {

    public static boolean isMouseOver(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
        return mouseX >= x1 && mouseY >= y1 && mouseX < x1 + x2 && mouseY < y1 + y2;
    }

    public static long int2Long(int left, int right) {
        return (((long) left) << 32) | (right & 0xffffffffL);
    }

    public static int[] long2Int(long packed) {
        return new int[] {(int) (packed >> 32), (int) packed};
    }
}
