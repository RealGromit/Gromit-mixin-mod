package com.gromit.gromitmod.utils.primitivewrapper;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

import java.util.Arrays;

public class GromitPosDouble {

    @Getter @Setter private double x;
    @Getter @Setter private double y;
    @Getter @Setter private double z;

    public GromitPosDouble(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GromitPosDouble(Entity entity) {
        this(entity.posX, entity.posY, entity.posZ);
    }

    public GromitPosDouble(Vec3 vec) {
        this(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public GromitPosDouble(Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public GromitPosDouble getAbove() {y++; return this;}

    public GromitPosDouble getBelow() {y--; return this;}

    public GromitPosDouble getNorth() {z--; return this;}

    public GromitPosDouble getSouth() {z++; return this;}

    public GromitPosDouble getWest() {x--; return this;}

    public GromitPosDouble getEast() {x++; return this;}

    public void setAbove() {y++;}

    public void setBelow() {y--;}

    public void setNorth() {z--;}

    public void setSouth() {z++;}

    public void setWest() {x--;}

    public void setEast() {x++;}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GromitPosDouble)) return false;
        GromitPosDouble gromitPosDouble = (GromitPosDouble) obj;
        return x == gromitPosDouble.x && y == gromitPosDouble.y && z == gromitPosDouble.z;
    }

    @Override
    public int hashCode() {return Arrays.hashCode(new double[] {x, y, z});}
}
