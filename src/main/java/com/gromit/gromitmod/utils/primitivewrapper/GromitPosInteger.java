package com.gromit.gromitmod.utils.primitivewrapper;

import net.minecraft.util.Vec3i;

import java.util.Arrays;

public class GromitPosInteger {

    private int x;
    private int y;
    private int z;

    public GromitPosInteger(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GromitPosInteger(Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GromitPosInteger)) return false;
        GromitPosInteger gromitPosInteger = (GromitPosInteger) obj;
        return x == gromitPosInteger.x && y == gromitPosInteger.y && z == gromitPosInteger.z;
    }

    @Override
    public int hashCode() {return Arrays.hashCode(new int[] {x, y, z});}

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z;
    }

    public GromitPosInteger getAbove() {y++; return this;}

    public GromitPosInteger getBelow() {y--; return this;}

    public GromitPosInteger getNorth() {z--; return this;}

    public GromitPosInteger getSouth() {z++; return this;}

    public GromitPosInteger getWest() {x--; return this;}

    public GromitPosInteger getEast() {x++; return this;}

    public void setAbove() {y++;}

    public void setBelow() {y--;}

    public void setNorth() {z--;}

    public void setSouth() {z++;}

    public void setWest() {x--;}

    public void setEast() {x++;}

    public int getX() {return x;}

    public int getY() {return y;}

    public int getZ() {return z;}
}
