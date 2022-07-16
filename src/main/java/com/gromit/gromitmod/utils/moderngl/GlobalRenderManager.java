package com.gromit.gromitmod.utils.moderngl;

import com.gromit.gromitmod.listener.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityFallingBlock;
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
    TextureAtlasSprite sand = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/sand");

    private final float[] sandUvCoords = {
            sand.getMaxU(), sand.getMaxV(),
            sand.getMaxU(), sand.getMinV(),
            sand.getMinU(), sand.getMinV(),
            sand.getMinU(), sand.getMaxV(),

            sand.getMaxU(), sand.getMaxV(),
            sand.getMaxU(), sand.getMinV(),
            sand.getMinU(), sand.getMinV(),
            sand.getMinU(), sand.getMaxV(),

            sand.getMaxU(), sand.getMaxV(),
            sand.getMaxU(), sand.getMinV(),
            sand.getMinU(), sand.getMinV(),
            sand.getMinU(), sand.getMaxV(),

            sand.getMaxU(), sand.getMaxV(),
            sand.getMaxU(), sand.getMinV(),
            sand.getMinU(), sand.getMinV(),
            sand.getMinU(), sand.getMaxV(),

            sand.getMaxU(), sand.getMaxV(),
            sand.getMaxU(), sand.getMinV(),
            sand.getMinU(), sand.getMinV(),
            sand.getMinU(), sand.getMaxV(),

            sand.getMaxU(), sand.getMaxV(),
            sand.getMaxU(), sand.getMinV(),
            sand.getMinU(), sand.getMinV(),
            sand.getMinU(), sand.getMaxV(),
    };

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


    private final GLObject tnt = new GLObject()
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

    private final GLObject sandObject = new GLObject()
            .bindVao()
            .addVbo("vbodata", new VBO())
            .addVbo("uv", new VBO())
            .populateVbo("uv", GL_ARRAY_BUFFER, sandUvCoords, GL_STATIC_DRAW)
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

    public GlobalRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void renderLoop(RenderWorldLastEvent event) {
        
        // No if statements to check if lists are empty so float arrays get instantiated every frame and I couldn't care less :sunglasses:
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

        boolean firstIterationSand = true;
        float cachedInterpolateXSand = 0;
        float cachedInterpolateYSand = 0;
        float cachedInterpolateZSand = 0;
        float[] vboDataSand = new float[72 + ClientTickEvent.cachedSandList.size() * 3];
        index = 72;
        for (EntityFallingBlock sand : ClientTickEvent.cachedSandList) {
            if (sand.ticksExisted == 0) {
                sand.lastTickPosX = sand.posX;
                sand.lastTickPosY = sand.posY;
                sand.lastTickPosZ = sand.posZ;
            }
            if (firstIterationSand) {
                cachedInterpolateXSand = (float) ((sand.lastTickPosX + (sand.posX - sand.lastTickPosX) * event.partialTicks) - renderManager.viewerPosX);
                cachedInterpolateYSand = (float) ((sand.lastTickPosY + (sand.posY - sand.lastTickPosY) * event.partialTicks) - renderManager.viewerPosY);
                cachedInterpolateZSand = (float) ((sand.lastTickPosZ + (sand.posZ - sand.lastTickPosZ) * event.partialTicks) - renderManager.viewerPosZ);
                FloatArrayBuilder.AABB2Floats(vboDataSand, cachedInterpolateXSand - 0.5f, cachedInterpolateYSand, cachedInterpolateZSand - 0.5f, cachedInterpolateXSand + 0.5f, cachedInterpolateYSand + 1, cachedInterpolateZSand + 0.5f);
                vboDataSand[index++] = 0;
                vboDataSand[index++] = 0;
                vboDataSand[index++] = 0;
                firstIterationSand = false;
                continue;
            }
            vboDataSand[index++] = (float) (((sand.lastTickPosX + (sand.posX - sand.lastTickPosX) * event.partialTicks) - cachedInterpolateXSand) - renderManager.viewerPosX);
            vboDataSand[index++] = (float) (((sand.lastTickPosY + (sand.posY - sand.lastTickPosY) * event.partialTicks) - cachedInterpolateYSand) - renderManager.viewerPosY);
            vboDataSand[index++] = (float) (((sand.lastTickPosZ + (sand.posZ - sand.lastTickPosZ) * event.partialTicks) - cachedInterpolateZSand) - renderManager.viewerPosZ);
        }

        tnt
                .populateVbo("vbodata", GL_ARRAY_BUFFER, vboData, GL_STREAM_DRAW)
                .unbindVbo(GL_ARRAY_BUFFER);

        sandObject
                .populateVbo("vbodata", GL_ARRAY_BUFFER, vboDataSand, GL_STREAM_DRAW)
                .unbindVbo(GL_ARRAY_BUFFER);

        glUseProgram(quadShader.getShaderProgram());
        glUniformMatrix4(quadShader.getUniform("projection"), false, WorldMatrices.finalProjection);
        tnt.renderInstanced(GL_QUADS, 0, 24, ClientTickEvent.cachedTntList.size());
        sandObject.renderInstanced(GL_QUADS, 0, 24, ClientTickEvent.cachedSandList.size());
        glUseProgram(0);
    }
}
