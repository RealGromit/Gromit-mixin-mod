package com.gromit.gromitmod.utils.moderngl;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class WorldMatrices {

    private static final Matrix4f projectionMatrix = new Matrix4f();
    private static final Matrix4f modelViewMatrix = new Matrix4f();
    public static FloatBuffer finalProjection = BufferUtils.createFloatBuffer(16);

    public static void updateMatrices(FloatBuffer projection, FloatBuffer modelView) {
        projectionMatrix.load((FloatBuffer) projection.flip());
        modelViewMatrix.load((FloatBuffer) modelView.flip());

        Matrix4f.mul(projectionMatrix, modelViewMatrix, projectionMatrix);
        finalProjection.clear();
        projectionMatrix.store(finalProjection);
        finalProjection.flip();
    }
}
