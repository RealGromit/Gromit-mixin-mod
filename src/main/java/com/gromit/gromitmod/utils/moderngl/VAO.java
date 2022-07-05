package com.gromit.gromitmod.utils.moderngl;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

public class VAO {

    @Getter private final int vaoID;
    private final List<VBO> vbos = new ArrayList<>();

    public VAO() {
        vaoID = glGenVertexArrays();
    }

    public VAO addVbo(VBO vbo) {
        vbos.add(vbo);
        return this;
    }

    public VAO addAttributePointers() {
        for (VBO vbo : vbos) {
            for (BufferArrayInfo info : vbo.getBufferArrayInfo()) {
                glVertexAttribPointer(info.getLayout(), info.getSize(), info.getType(), false, info.getStride(), info.getBufferOffset());
                glEnableVertexAttribArray(info.getLayout());
            }
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

    public VAO bind() {
        glBindVertexArray(vaoID);
        return this;
    }

    public VAO unbind() {
        glBindVertexArray(0);
        return this;
    }
}
