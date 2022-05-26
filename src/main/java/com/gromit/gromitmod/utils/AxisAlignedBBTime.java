package com.gromit.gromitmod.utils;

import net.minecraft.util.AxisAlignedBB;

import java.util.Arrays;

public class AxisAlignedBBTime {

    private long time;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public AxisAlignedBBTime(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, long time) {
        this.time = time;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    public AxisAlignedBBTime(AxisAlignedBB box, long time) {
        this(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, time);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AxisAlignedBBTime)) return false;
        AxisAlignedBBTime box = (AxisAlignedBBTime) obj;
        return minX == box.minX && minY == box.minY && minZ == box.minZ;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new double[] {minX, minY, minZ});
    }

    @Override
    public String toString() {
        return "minX: " + minX + " minY: " + minY + " minZ: " + minZ + " maxX: " + maxX + " maxY: " + maxY + " maxZ: " + maxZ;
    }

    public void expandToBlock() {
        if (minX % 1 != 0) minX = Math.round(minX);
        if (minZ % 1 != 0) minZ = Math.round(minZ);
        if (maxX % 1 != 0) maxX = Math.round(maxX);
        if (maxZ % 1 != 0) maxZ = Math.round(maxZ);
    }

    public void floorVertically() {
        if (minY % 1 != 0) minY = Math.floor(minY);
        if (maxY % 1 != 0) maxY = Math.floor(maxY);
    }

    public void ceilVertically() {
        if (minY % 1 != 0) minY = Math.ceil(minY);
        if (maxY % 1 != 0) maxY = Math.ceil(maxY);
    }

    public void roundY() {
        if (minY % 1 != 0) minY = Math.round(minY);
        if (maxY % 1 != 0) maxY = Math.round(maxY);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}