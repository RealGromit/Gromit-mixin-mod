package com.gromit.gromitmod.utils.moderngl;

public class FloatArrayBuilder {

    public static float[] AABB2Floats(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        float[] data = new float[72];
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
        return data;
    }
}
