package com.gromit.gromitmod.utils.moderngl;

public class FloatArrayBuilder {

    private float[] finalArray;
    private float[] uvCoords;

    public static void AABB2Floats(float[] data, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        int index = 0;
        data[index++] = minX;
        data[index++] = minY;
        data[index++] = minZ;
        data[index++] = minX;
        data[index++] = maxY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = maxY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = minY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = minY;
        data[index++] = maxZ;
        data[index++] = maxX;
        data[index++] = maxY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = maxY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = minY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = minY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = maxY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = maxY;
        data[index++] = minZ;
        data[index++] = minX;
        data[index++] = minY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = minY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = maxY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = maxY;
        data[index++] = maxZ;
        data[index++] = maxX;
        data[index++] = minY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = maxY;
        data[index++] = minZ;
        data[index++] = minX;
        data[index++] = maxY;
        data[index++] = maxZ;
        data[index++] = maxX;
        data[index++] = maxY;
        data[index++] = maxZ;
        data[index++] = maxX;
        data[index++] = maxY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = minY;
        data[index++] = minZ;
        data[index++] = maxX;
        data[index++] = minY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = minY;
        data[index++] = maxZ;
        data[index++] = minX;
        data[index++] = minY;
        data[index] = minZ;
    }

    public FloatArrayBuilder UV2Floats(float minX, float minY, float maxX, float maxY) {
        uvCoords = new float[] {
                minX, minY,
                minX, maxY,
                maxX, maxY,
                maxX, minY
        };
        return this;
    }
}
