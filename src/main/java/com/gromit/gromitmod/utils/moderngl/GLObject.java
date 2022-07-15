package com.gromit.gromitmod.utils.moderngl;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.function.Consumer;

public class GLObject {

    private final int vaoID;
    private final HashMap<String, VBO> vboMap = new HashMap<>();

    public GLObject() {
        vaoID = glGenVertexArrays();
    }

    public GLObject addVbo(String vboName, VBO vbo) {
        vboMap.put(vboName, vbo);
        return this;
    }

    public GLObject populateVbo(String vboName, int target, float[] data, int enumDraw) {
        VBO vbo = vboMap.get(vboName);
        bindVbo(vbo, target);
        glBufferData(target, createBuffer(data), enumDraw);
        return this;
    }

    @SafeVarargs
    public final GLObject populateVao(Consumer<GLObject>... consumers) {
        for (Consumer<GLObject> consumer : consumers) {
            if (consumer == null) continue;
            consumer.accept(this);
        }
        return this;
    }

    public GLObject populateVbo(String vboName, int target, double[] data, int enumDraw) {
        VBO vbo = vboMap.get(vboName);
        bindVbo(vbo, target);
        glBufferData(target, createBuffer(data), enumDraw);
        return this;
    }

    public GLObject populateVbo(String vboName, int target, int[] data, int enumDraw, BufferArrayInfo... bufferArrayInfos) {
        VBO vbo = vboMap.get(vboName);
        bindVbo(vbo, target);
        glBufferData(target, createBuffer(data), enumDraw);
        for (BufferArrayInfo info : bufferArrayInfos) {
            if (info == null) continue;
            glVertexAttribPointer(info.getLayout(), info.getSize(), info.getType(), false, info.getStride(), info.getBufferOffset());
            glEnableVertexAttribArray(info.getLayout());
            if (info.getConsumer() != null) info.getConsumer().accept(info);
        }
        return this;
    }

    public GLObject populateVbo(String vboName, int target, byte[] data, int enumDraw, BufferArrayInfo... bufferArrayInfos) {
        VBO vbo = vboMap.get(vboName);
        bindVbo(vbo, target);
        glBufferData(target, createBuffer(data), enumDraw);
        for (BufferArrayInfo info : bufferArrayInfos) {
            if (info == null) continue;
            glVertexAttribPointer(info.getLayout(), info.getSize(), info.getType(), false, info.getStride(), info.getBufferOffset());
            glEnableVertexAttribArray(info.getLayout());
            if (info.getConsumer() != null) info.getConsumer().accept(info);
        }
        return this;
    }

    public void render(int primitive, int first, int last) {
        glBindVertexArray(vaoID);
        glDrawArrays(primitive, first, last);
        glBindVertexArray(0);
    }

    public void renderElements(int primitive, int indices, int type, int first) {
        glBindVertexArray(vaoID);
        glDrawElements(primitive, indices, type, first);
        glBindVertexArray(0);
    }

    public void renderInstanced(int primitive, int first, int last, int count) {
        glBindVertexArray(vaoID);
        glDrawArraysInstanced(primitive, first, last, count);
        glBindVertexArray(0);
    }

    public void renderElementsInstanced(int primitive, int indices, int type, int first, int count) {
        glBindVertexArray(vaoID);
        glDrawElementsInstanced(primitive, indices, type, first, count);
        glBindVertexArray(0);
    }

    public GLObject bindVao() {
        glBindVertexArray(vaoID);
        return this;
    }

    public GLObject unbindVao() {
        glBindVertexArray(0);
        return this;
    }

    public GLObject bindVbo(String vboName, int target) {
        glBindBuffer(target, vboMap.get(vboName).getVboID());
        return this;
    }

    public GLObject bindVbo(VBO vbo, int target) {
        glBindBuffer(target, vbo.getVboID());
        return this;
    }

    public GLObject unbindVbo(int target) {
        glBindBuffer(target, 0);
        return this;
    }

    private FloatBuffer createBuffer(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
        floatBuffer.put(data);
        floatBuffer.flip();
        return floatBuffer;
    }

    private DoubleBuffer createBuffer(double[] data) {
        DoubleBuffer doubleBuffer = BufferUtils.createDoubleBuffer(data.length);
        doubleBuffer.put(data);
        doubleBuffer.flip();
        return doubleBuffer;
    }

    private IntBuffer createBuffer(int[] data) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(data.length);
        intBuffer.put(data);
        intBuffer.flip();
        return intBuffer;
    }

    private ByteBuffer createBuffer(byte[] data) {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(data.length);
        byteBuffer.put(data);
        byteBuffer.flip();
        return byteBuffer;
    }
}
