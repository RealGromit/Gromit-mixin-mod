package com.gromit.gromitmod.utils.moderngl;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class GLObject {

    private final VAO vao;
    private final List<VBO> vbos = new ArrayList<>();

    public GLObject() {
        vao = new VAO();
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

    public void render(int primitive, int first, int last) {
        vao.bind();
        glDrawArrays(primitive, first, last);
        vao.unbind();
    }

    public void renderElements(int primitive, int indices, int type, int first) {
        vao.bind();
        glDrawElements(primitive, indices, type, first);
        vao.unbind();
    }

    public void renderInstanced(int primitive, int first, int last, int count) {
        vao.bind();
        glDrawArraysInstanced(primitive, first, last, count);
        vao.unbind();
    }

    public void renderElementsInstanced(int primitive, int indices, int type, int first, int count) {
        vao.bind();
        glDrawElementsInstanced(primitive, indices, type, first, count);
        vao.unbind();
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