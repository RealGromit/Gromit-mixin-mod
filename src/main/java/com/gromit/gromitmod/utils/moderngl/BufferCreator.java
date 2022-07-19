package com.gromit.gromitmod.utils.moderngl;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferCreator {

    public static FloatBuffer createBuffer(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
        floatBuffer.put(data);
        floatBuffer.flip();
        return floatBuffer;
    }

    public static DoubleBuffer createBuffer(double[] data) {
        DoubleBuffer doubleBuffer = BufferUtils.createDoubleBuffer(data.length);
        doubleBuffer.put(data);
        doubleBuffer.flip();
        return doubleBuffer;
    }

    public static IntBuffer createBuffer(int[] data) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(data.length);
        intBuffer.put(data);
        intBuffer.flip();
        return intBuffer;
    }

    public static ByteBuffer createBuffer(byte[] data) {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(data.length);
        byteBuffer.put(data);
        byteBuffer.flip();
        return byteBuffer;
    }
}
