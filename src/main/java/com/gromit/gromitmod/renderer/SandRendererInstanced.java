package com.gromit.gromitmod.renderer;

import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.MathUtils;
import com.gromit.gromitmod.utils.moderngl.GLObject;
import net.minecraft.block.BlockSand;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL15.*;

public class SandRendererInstanced extends CustomInstancedEntityRenderer {

    TextureAtlasSprite sandTexture = textureMap.getAtlasSprite("minecraft:blocks/sand");

    private final float[] sandUvCoords = {
            sandTexture.getMaxU(), sandTexture.getMaxV(),
            sandTexture.getMaxU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMaxV(),

            sandTexture.getMaxU(), sandTexture.getMaxV(),
            sandTexture.getMaxU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMaxV(),

            sandTexture.getMaxU(), sandTexture.getMaxV(),
            sandTexture.getMaxU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMaxV(),

            sandTexture.getMaxU(), sandTexture.getMaxV(),
            sandTexture.getMaxU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMaxV(),

            sandTexture.getMaxU(), sandTexture.getMaxV(),
            sandTexture.getMaxU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMaxV(),

            sandTexture.getMaxU(), sandTexture.getMaxV(),
            sandTexture.getMaxU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMinV(),
            sandTexture.getMinU(), sandTexture.getMaxV(),
    };

    private final GLObject sand = new GLObject()
            .bindVao()
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .populateVbo(0, cubeCoords, GL_STATIC_DRAW)
            .populateVao(0, 3, GL_FLOAT, 0, 0)
            .bindVbo(1)
            .populateVao(1, 3, GL_FLOAT, 0, 0)
            .divisor(1, 1)
            .populateVbo(2, sandUvCoords, GL_STATIC_DRAW)
            .populateVao(2, 2, GL_FLOAT, 0, 0)
            .unbindVbo(2)
            .unbindVao();

    public SandRendererInstanced(Class<? extends Entity> instancedClass) {
        super(instancedClass);
    }

    @Override
    public void render() {
        if (offsets.isEmpty()) return;

        float[] array = ClientUtils.translateFloatList(offsets);
        sand.populateVbo(1, array, GL_STREAM_DRAW);
        sand.renderInstanced(GL_QUADS, 0, 24, array.length / 3);
        offsets.clear();
    }

    @Override
    public boolean fillOffsets(Entity entity, float partialTicks) {
        if (((EntityFallingBlock) entity).getBlock().getBlock() instanceof BlockSand) {
            if (entity.ticksExisted == 0) {
                entity.lastTickPosX = entity.posX;
                entity.lastTickPosY = entity.posY;
                entity.lastTickPosZ = entity.posZ;
            }
            offsets.add(MathUtils.interpolate((float) entity.posX, (float) entity.lastTickPosX, partialTicks));
            offsets.add(MathUtils.interpolate((float) entity.posY, (float) entity.lastTickPosY, partialTicks));
            offsets.add(MathUtils.interpolate((float) entity.posZ, (float) entity.lastTickPosZ, partialTicks));
            return true;
        }
        return false;
    }
}
