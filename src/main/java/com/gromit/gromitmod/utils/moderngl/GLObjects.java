package com.gromit.gromitmod.utils.moderngl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class GLObjects {

    public static final GLObject tntRenderer = new GLObject(GL_QUADS, 0, 24)
            .bindVao()
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .bindVbo(0)
            .populateVao(0, 3, GL_FLOAT, 0, 0)
            .divisor(0, 1)
            .populateVbo(1, CommonShapeCoords.cubeCoords, GL_STATIC_DRAW)
            .populateVao(1, 3, GL_FLOAT, 0, 0)
            .populateVbo(2, UvCoords.tntUv, GL_STATIC_DRAW)
            .populateVao(2, 2, GL_FLOAT, 0, 0)
            .unbindVbo(2)
            .unbindVao();

    public static final GLObject sandRenderer = new GLObject(GL_QUADS, 0, 24)
            .bindVao()
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .bindVbo(0)
            .populateVao(0, 3, GL_FLOAT, 0, 0)
            .divisor(0, 1)
            .populateVbo(1, CommonShapeCoords.cubeCoords, GL_STATIC_DRAW)
            .populateVao(1, 3, GL_FLOAT, 0, 0)
            .populateVbo(2, UvCoords.getUvCoords(UvCoords.sandTexture), GL_STATIC_DRAW)
            .populateVao(2, 2, GL_FLOAT, 0, 0)
            .unbindVbo(2)
            .unbindVao();

    public static final GLObject gravelRenderer = new GLObject(GL_QUADS, 0, 24)
            .bindVao()
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .bindVbo(0)
            .populateVao(0, 3, GL_FLOAT, 0, 0)
            .divisor(0, 1)
            .populateVbo(1, CommonShapeCoords.cubeCoords, GL_STATIC_DRAW)
            .populateVao(1, 3, GL_FLOAT, 0, 0)
            .populateVbo(2, UvCoords.getUvCoords(UvCoords.gravelTexture), GL_STATIC_DRAW)
            .populateVao(2, 2, GL_FLOAT, 0, 0)
            .unbindVbo(2)
            .unbindVao();

    public static final GLObject redSandRenderer = new GLObject(GL_QUADS, 0, 24)
            .bindVao()
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .bindVbo(0)
            .populateVao(0, 3, GL_FLOAT, 0, 0)
            .divisor(0, 1)
            .populateVbo(1, CommonShapeCoords.cubeCoords, GL_STATIC_DRAW)
            .populateVao(1, 3, GL_FLOAT, 0, 0)
            .populateVbo(2, UvCoords.getUvCoords(UvCoords.redSandTexture), GL_STATIC_DRAW)
            .populateVao(2, 2, GL_FLOAT, 0, 0)
            .unbindVbo(2)
            .unbindVao();

    public static final GLObject guiRoundedRectangle = new GLObject(GL_QUADS, 0, 4)
            .bindVao()
            .addVbo(GL_ARRAY_BUFFER)
            .bindVbo(0)
            .populateVao(0, 2, GL_FLOAT, 0, 0)
            .unbindVbo(0)
            .unbindVao();
}
