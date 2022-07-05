package com.gromit.gromitmod.utils.moderngl;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VBO {

    @Getter private final int vboID;
    @Setter private float[] data;
    @Setter private FloatBuffer floatBuffer;
    @Setter private int lastEnumTarget;
    @Getter @Setter private BufferArrayInfo[] bufferArrayInfo;

    public VBO() {vboID = glGenBuffers();}

    public VBO bind(int enumTarget) {
        glBindBuffer(enumTarget, vboID);
        setLastEnumTarget(enumTarget);
        return this;
    }

    public VBO unbind() {
        glBindBuffer(lastEnumTarget, 0);
        return this;
    }

    public VBO populateVBO(float[] data, int enumDraw, BufferArrayInfo... bufferArrayInfo) {
        setData(data);
        setFloatBuffer(createBuffer(data));
        glBufferData(lastEnumTarget, floatBuffer, enumDraw);
        this.bufferArrayInfo = bufferArrayInfo;
        return this;
    }

    private FloatBuffer createBuffer(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
        floatBuffer.put(data);
        floatBuffer.flip();
        return floatBuffer;
    }
}
