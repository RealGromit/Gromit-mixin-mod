package com.gromit.gromitmod.utils.moderngl;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class GLObject {

    private final VAO vao;
    private final List<VBO> vbos = new ArrayList<>();
    private final int primitive, first, last;
    public GLObject(int primitive, int first, int last) {
        vao = new VAO();
        this.primitive = primitive;
        this.first = first;
        this.last = last;
    }

    public GLObject addVbo(int bufferTarget) {
        vbos.add(new VBO(bufferTarget));
        return this;
    }

    public GLObject populateVbo(int index, float[] data, int enumDraw) {
        VBO vbo = vbos.get(index).bind();
        glBufferData(vbo.getBufferTarget(), BufferCreator.createBuffer(data), enumDraw);
        return this;
    }

    public GLObject populateVbo(int index, byte[] data, int enumDraw) {
        VBO vbo = vbos.get(index).bind();
        glBufferData(vbo.getBufferTarget(), BufferCreator.createBuffer(data), enumDraw);
        return this;
    }

    public GLObject populateVao(int index, int size, int type, int stride, int bufferOffset) {
        glVertexAttribPointer(index, size, type, false, stride, bufferOffset);
        glEnableVertexAttribArray(index);
        return this;
    }

    public GLObject divisor(int index, int instances) {
        glVertexAttribDivisor(index, instances);
        return this;
    }

    public void render() {
        vao.bind();
        glDrawArrays(primitive, first, last);
    }

    public void renderInstanced(int count) {
        vao.bind();
        glDrawArraysInstanced(primitive, first, last, count);
    }

    public GLObject bindVao() {
        vao.bind();
        return this;
    }

    public GLObject unbindVao() {
        vao.unbind();
        return this;
    }

    public GLObject bindVbo(int index) {
        vbos.get(index).bind();
        return this;
    }

    public GLObject unbindVbo(int index) {
        vbos.get(index).unbind();
        return this;
    }
}