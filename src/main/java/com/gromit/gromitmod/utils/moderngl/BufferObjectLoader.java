package com.gromit.gromitmod.utils.moderngl;


import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

// WIP :pray:
public class BufferObjectLoader {

    private VAO vao;
    public static FloatBuffer projectionMatrix;
    public static FloatBuffer modelMatrix;
    private final Shader triangle = new Shader(new ResourceLocation("astrix", "triangle.glsl"),
            new ResourceLocation("astrix", "trianglecolor.glsl"));

    public BufferObjectLoader() {
        AxisAlignedBB box = new AxisAlignedBB(1, 4, 1, 2, 5, 2);
        float[] vertices = {
                (float) box.maxX, (float) box.maxY, (float) box.minZ,
                (float) box.maxX, (float) box.minY, (float) box.minZ,
                (float) box.maxX, (float) box.minY, (float) box.maxZ,
                (float) box.maxX, (float) box.maxY, (float) box.maxZ
        };
        float[] colors = {
                1, 1, 1
        };
        TextureAtlasSprite tntSide = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/tnt_side");
        float[] texCoords = {
                tntSide.getMinU(), tntSide.getMinV(),
                tntSide.getMinU(), tntSide.getMaxV(),
                tntSide.getMaxU(), tntSide.getMaxV(),
                tntSide.getMaxU(), tntSide.getMinV()
        };

        //BufferedImage image;
        //try {
        //    image = ImageIO.read(new File("C:\\Users\\Gromit\\Desktop\\Git clones\\Gromit mixin mod\\src\\main\\resources\\assets\\astrix\\sand.png"));
        //} catch (IOException e) {throw new RuntimeException(e);}
//
        //int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        //ByteBuffer byteBuffer = BufferUtils.createByteBuffer(pixels.length * 3);
//
        //for (int y = 0; y < image.getHeight(); ++y) {
        //    for (int x = 0; x < image.getWidth(); ++x) {
        //        final int pixel = pixels[y * image.getWidth() + x];
        //        byteBuffer.put((byte)(pixel >> 16 & 0xFF));
        //        byteBuffer.put((byte)(pixel >> 8 & 0xFF));
        //        byteBuffer.put((byte)(pixel & 0xFF));
        //    }
        //}
        //byteBuffer.flip();

        float[] offsets = new float[200];
        int index = 0;
        float offset = 0.1f;
        for(int y = -10; y < 10; y += 2) {
            for(int x = -10; x < 10; x += 2) {
                offsets[index++] = x / 10f + offset;
                offsets[index++] = y / 10f + offset;
            }
        }

        float[] quadVertices = {
                -0.05f, 0.05f,
                -0.05f, -0.05f,
                0.05f, -0.05f,
                0.05f, 0.05f,
                1, 0, 0,
                0, 1, 0,
                0, 0, 1,
                1, 1, 1
        };

        float[] finalVertices = new float[220];
        int index1 = 0;
        for (float vertex : quadVertices) {
            finalVertices[index1++] = vertex;
        }
        for (float offsetss : offsets) {
            finalVertices[index1++] = offsetss;
        }

        VBO vbo = new VBO();
        vao = new VAO()
                .bind()
                .addVbo(vbo
                        .bind(GL_ARRAY_BUFFER)
                        .populateVBO(finalVertices, GL_STATIC_DRAW,
                                new BufferArrayInfo(0, 2, GL_FLOAT, 0, 0),
                                new BufferArrayInfo(1, 3, GL_FLOAT, 0, 32),
                                new BufferArrayInfo(2, 2, GL_FLOAT, 0, 80)))
                .addAttributePointers();
        glVertexAttribDivisor(2, 1);
        vao.unbind();
        vbo.unbind();
    }

    public void renderVAO() {
        glUseProgram(triangle.getShaderProgram());
        vao.renderInstanced(GL_QUADS, 0, 4, 100);
        glUseProgram(0);
    }

    private FloatBuffer createFloatBuffer(float[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
        return buffer;
    }

    private ByteBuffer createByteBuffer(byte[] vertices) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
        return buffer;
    }

    private DoubleBuffer createDoubleBuffer(double[] vertices) {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
        return buffer;
    }
}
