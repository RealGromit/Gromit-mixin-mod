package com.gromit.gromitmod.utils.moderngl;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

import java.nio.FloatBuffer;
import java.util.HashMap;

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

    public GLObject populateVbo(String vboName, float[] data, int enumDraw, BufferArrayInfo... bufferArrayInfos) {
        VBO vbo = vboMap.get(vboName);
        bindVbo(vbo);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(data), enumDraw);
        for (BufferArrayInfo info : bufferArrayInfos) {
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

    public void renderInstanced(int primitive, int first, int last, int count) {
        glBindVertexArray(vaoID);
        glDrawArraysInstanced(primitive, first, last, count);
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

    public GLObject bindVbo(String vboName) {
        glBindBuffer(GL_ARRAY_BUFFER, vboMap.get(vboName).getVboID());
        return this;
    }

    public GLObject bindVbo(VBO vbo) {
        glBindBuffer(GL_ARRAY_BUFFER, vbo.getVboID());
        return this;
    }

    public GLObject unbindVbo() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return this;
    }

    private FloatBuffer createBuffer(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
        floatBuffer.put(data);
        floatBuffer.flip();
        return floatBuffer;
    }
}
