package com.gromit.gromitmod.renderer;

import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.MathUtils;
import com.gromit.gromitmod.utils.moderngl.GLObject;
import net.minecraft.block.BlockGravel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL15.*;

public class GravelRendererInstanced extends CustomInstancedEntityRenderer {

    TextureAtlasSprite gravelTexture = textureMap.getAtlasSprite("minecraft:blocks/gravel");

    private final float[] gravelUvCoords = {
            gravelTexture.getMaxU(), gravelTexture.getMaxV(),
            gravelTexture.getMaxU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMaxV(),

            gravelTexture.getMaxU(), gravelTexture.getMaxV(),
            gravelTexture.getMaxU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMaxV(),

            gravelTexture.getMaxU(), gravelTexture.getMaxV(),
            gravelTexture.getMaxU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMaxV(),

            gravelTexture.getMaxU(), gravelTexture.getMaxV(),
            gravelTexture.getMaxU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMaxV(),

            gravelTexture.getMaxU(), gravelTexture.getMaxV(),
            gravelTexture.getMaxU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMaxV(),

            gravelTexture.getMaxU(), gravelTexture.getMaxV(),
            gravelTexture.getMaxU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMinV(),
            gravelTexture.getMinU(), gravelTexture.getMaxV(),
    };

    private final GLObject gravel = new GLObject()
            .bindVao()
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .populateVbo(0, cubeCoords, GL_STATIC_DRAW)
            .populateVao(0, 3, GL_FLOAT, 0, 0)
            .bindVbo(1)
            .populateVao(1, 3, GL_FLOAT, 0, 0)
            .divisor(1, 1)
            .populateVbo(2, gravelUvCoords, GL_STATIC_DRAW)
            .populateVao(2, 2, GL_FLOAT, 0, 0)
            .unbindVbo(2)
            .unbindVao();

    public GravelRendererInstanced(Class<? extends Entity> instancedClass) {
        super(instancedClass);
    }

    @Override
    public void render() {
        if (offsets.isEmpty()) return;

        float[] array = ClientUtils.translateFloatList(offsets);
        gravel.populateVbo(1, array, GL_STREAM_DRAW);
        gravel.renderInstanced(GL_QUADS, 0, 24, array.length / 3);
        offsets.clear();
    }

    @Override
    public boolean fillOffsets(Entity entity, float partialTicks) {
        if (((EntityFallingBlock) entity).getBlock().getBlock() instanceof BlockGravel) {
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
