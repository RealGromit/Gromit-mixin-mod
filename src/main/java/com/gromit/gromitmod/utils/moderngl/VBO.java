package com.gromit.gromitmod.utils.moderngl;

import lombok.Getter;
import lombok.Setter;

import static org.lwjgl.opengl.GL15.glGenBuffers;

// Wrapper class
public class VBO {

    @Getter private final int vboID;
    @Getter @Setter private BufferArrayInfo[] bufferArrayInfo;

    public VBO() {vboID = glGenBuffers();}
}
