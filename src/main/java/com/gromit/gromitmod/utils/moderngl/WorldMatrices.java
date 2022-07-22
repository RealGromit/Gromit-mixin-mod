package com.gromit.gromitmod.utils.moderngl;

import org.lwjgl.BufferUtils;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

public class WorldMatrices {

    private static final Matrix4f projectionMatrix = new Matrix4f();
    private static final Matrix4f modelViewMatrix = new Matrix4f();
    public static FloatBuffer guiProjection = BufferUtils.createFloatBuffer(16);
    public static FloatBuffer worldProjection = BufferUtils.createFloatBuffer(16);

    static {
        guiProjection = new Matrix4f()
                .setOrtho(0, 1920, 0, 1080, 0, 1)
                .get(guiProjection);
    }

    public static void updateMatrices(float[] projection, float[] modelView) {
        projectionMatrix.set(projection);
        modelViewMatrix.set(modelView);
        projectionMatrix.mul(modelViewMatrix);
        projectionMatrix.get(worldProjection);
    }
}