package com.gromit.gromitmod.utils.moderngl;

import com.gromit.gromitmod.listener.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class GlobalRenderManager {

    private final TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
    private final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
    private final Shader quadShader = new Shader(new ResourceLocation("astrix", "triangle.glsl"),
            new ResourceLocation("astrix", "trianglecolor.glsl"),
            "projection");
    
    TextureAtlasSprite tntTop = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/tnt_top");
    TextureAtlasSprite tntSide = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/tnt_side");
    TextureAtlasSprite tntBottom = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/tnt_bottom");

    private final float[] uvCoords = {
            tntSide.getMaxU(), tntSide.getMaxV(),
            tntSide.getMaxU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMaxV(),

            tntSide.getMaxU(), tntSide.getMaxV(),
            tntSide.getMaxU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMaxV(),

            tntSide.getMaxU(), tntSide.getMaxV(),
            tntSide.getMaxU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMaxV(),

            tntSide.getMaxU(), tntSide.getMaxV(),
            tntSide.getMaxU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMinV(),
            tntSide.getMinU(), tntSide.getMaxV(),

            tntTop.getMaxU(), tntTop.getMinV(),
            tntTop.getMinU(), tntTop.getMinV(),
            tntTop.getMinU(), tntTop.getMaxV(),
            tntTop.getMaxU(), tntTop.getMaxV(),

            tntBottom.getMinU(), tntBottom.getMinV(),
            tntBottom.getMinU(), tntBottom.getMaxV(),
            tntBottom.getMaxU(), tntBottom.getMaxV(),
            tntBottom.getMaxU(), tntBottom.getMinV()
    };


    private final GLObject quad = new GLObject()
            .bindVao()
            .addVbo("vbodata", new VBO())
            .addVbo("uv", new VBO())
            .populateVbo("uv", GL_ARRAY_BUFFER, uvCoords, GL_STATIC_DRAW)
            .populateVao(buffer -> {
                glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
                glEnableVertexAttribArray(2);
            })
            .bindVbo("vbodata", GL_ARRAY_BUFFER)
            .populateVao(buffer -> {
                glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
                glEnableVertexAttribArray(0);
                glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 288);
                glEnableVertexAttribArray(1);
                glVertexAttribDivisor(1, 1);
            })
            .unbindVbo(GL_ARRAY_BUFFER)
            .unbindVao();

    private final BufferArrayInfo position = new BufferArrayInfo(0, 3, GL_FLOAT, 0, 0, null);
    private final BufferArrayInfo offsets = new BufferArrayInfo(1, 3, GL_FLOAT, 0, 288, buffer -> glVertexAttribDivisor(1, 1));

    public GlobalRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void renderLoop(RenderWorldLastEvent event) {
        if (ClientTickEvent.cachedTntList.isEmpty()) return;
        boolean firstIteration = true;
        float cachedInterpolateX = 0;
        float cachedInterpolateY = 0;
        float cachedInterpolateZ = 0;
        float[] vboData = new float[72 + ClientTickEvent.cachedTntList.size() * 3];
        int index = 72;
        for (EntityTNTPrimed tnt : ClientTickEvent.cachedTntList) {
            if (tnt.ticksExisted == 0) {
                tnt.lastTickPosX = tnt.posX;
                tnt.lastTickPosY = tnt.posY;
                tnt.lastTickPosZ = tnt.posZ;
            }
            if (firstIteration) {
                cachedInterpolateX = (float) ((tnt.lastTickPosX + (tnt.posX - tnt.lastTickPosX) * event.partialTicks) - renderManager.viewerPosX);
                cachedInterpolateY = (float) ((tnt.lastTickPosY + (tnt.posY - tnt.lastTickPosY) * event.partialTicks) - renderManager.viewerPosY);
                cachedInterpolateZ = (float) ((tnt.lastTickPosZ + (tnt.posZ - tnt.lastTickPosZ) * event.partialTicks) - renderManager.viewerPosZ);
                FloatArrayBuilder.AABB2Floats(vboData, cachedInterpolateX - 0.5f, cachedInterpolateY, cachedInterpolateZ - 0.5f, cachedInterpolateX + 0.5f, cachedInterpolateY + 1, cachedInterpolateZ + 0.5f);
                vboData[index++] = 0;
                vboData[index++] = 0;
                vboData[index++] = 0;
                firstIteration = false;
                continue;
            }
            vboData[index++] = (float) (((tnt.lastTickPosX + (tnt.posX - tnt.lastTickPosX) * event.partialTicks) - cachedInterpolateX) - renderManager.viewerPosX);
            vboData[index++] = (float) (((tnt.lastTickPosY + (tnt.posY - tnt.lastTickPosY) * event.partialTicks) - cachedInterpolateY) - renderManager.viewerPosY);
            vboData[index++] = (float) (((tnt.lastTickPosZ + (tnt.posZ - tnt.lastTickPosZ) * event.partialTicks) - cachedInterpolateZ) - renderManager.viewerPosZ);
        }

        quad
                .populateVbo("vbodata", GL_ARRAY_BUFFER, vboData, GL_STREAM_DRAW)
                .unbindVbo(GL_ARRAY_BUFFER);

        glUseProgram(quadShader.getShaderProgram());
        glUniformMatrix4(quadShader.getUniform("projection"), false, WorldMatrices.finalProjection);
        quad.renderInstanced(GL_QUADS, 0, 24, ClientTickEvent.cachedTntList.size());
        glUseProgram(0);
    }
}
