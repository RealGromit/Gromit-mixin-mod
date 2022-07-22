package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.utils.primitivewrapper.GromitPosDouble;
import jline.internal.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

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

    public static double getDistance(GromitPosDouble pos1, GromitPosDouble pos2) {
        double deltaX = pos1.getX() - pos2.getX();
        double deltaY = pos1.getY() - pos2.getY();
        double deltaZ = pos1.getZ() - pos2.getZ();
        return MathHelper.sqrt_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    public static double getDistance(double x, double y, double z, double x1, double y1, double z1) {
        double deltaX = x - x1;
        double deltaY = y - y1;
        double deltaZ = z - z1;
        return MathHelper.sqrt_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    @Nullable
    public static GromitPosDouble getRange(GromitPosDouble power, GromitPosDouble projectile, int powerAmount, int ticks) {
        double d12 = getDistance(power, projectile) / 8;
        if (d12 <= 1.0) {
            double d5 = projectile.getX() - power.getX();
            double d7 = projectile.getY() - power.getY();
            double d9 = projectile.getZ() - power.getZ();
            double d13 = MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9 * d9);
            if (d13 != 0.0) {
                d5 /= d13;
                d7 /= d13;
                d9 /= d13;
                double d10 = 1.0 - d12;

                double x;
                double y;
                double z;
                x = d5 * d10 * powerAmount;
                y = d7 * d10 * powerAmount;
                z = d9 * d10 * powerAmount;

                double tempZ = z;
                for (int i = 0; i < ticks - 1; i++) {
                    z *= 0.9800000190734863;
                    tempZ += z;
                }
                return new GromitPosDouble(x, y, tempZ);
            }
        }
        return null;
    }

    public static float[] entity2Coords(Entity entity, float partialTicks) {
        return new float[] {
                interpolate((float) entity.posX, (float) entity.lastTickPosX, partialTicks),
                interpolate((float) entity.posY, (float) entity.lastTickPosY, partialTicks),
                interpolate((float) entity.posZ, (float) entity.lastTickPosZ, partialTicks)
        };
    }

    public static float interpolate(float currentPos, float lastTickPos, float partialTicks) {
        return lastTickPos + (currentPos - lastTickPos) * partialTicks;
    }

    public static float interpolateCameraDelta(float currentPos, float lastTickPos, float partialTicks, float camera) {
        return lastTickPos + (currentPos - lastTickPos) * partialTicks - camera;
    }
}
