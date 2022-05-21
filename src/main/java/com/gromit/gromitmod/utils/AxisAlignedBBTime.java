package com.gromit.gromitmod.utils;

import net.minecraft.util.AxisAlignedBB;

import java.util.Arrays;

public class AxisAlignedBBTime extends AxisAlignedBB {

    private final long time;

    public AxisAlignedBBTime(double x1, double y1, double z1, double x2, double y2, double z2, long time) {
        super(x1, y1, z1, x2, y2, z2);
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AxisAlignedBB)) return false;
        AxisAlignedBB box = (AxisAlignedBB) obj;
        return minX == box.minX && minY == box.minY && minZ == box.minZ;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new double[] {minX, minY, minZ});
    }

    public long getTime() {
        return time;
    }
}