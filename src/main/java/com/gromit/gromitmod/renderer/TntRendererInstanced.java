package com.gromit.gromitmod.renderer;

import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.MathUtils;
import com.gromit.gromitmod.utils.moderngl.GLObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL15.*;

public class TntRendererInstanced extends CustomInstancedEntityRenderer {

    private final TextureAtlasSprite tntTop = textureMap.getAtlasSprite("minecraft:blocks/tnt_top");
    private final TextureAtlasSprite tntSide = textureMap.getAtlasSprite("minecraft:blocks/tnt_side");
    private final TextureAtlasSprite tntBottom = textureMap.getAtlasSprite("minecraft:blocks/tnt_bottom");

    private final float[] tntUvCoords = {
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
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .addVbo(GL_ARRAY_BUFFER)
            .populateVbo(0, cubeCoords, GL_STATIC_DRAW)
            .populateVao(0, 3, GL_FLOAT, 0, 0)
            .bindVbo(1)
            .populateVao(1, 3, GL_FLOAT, 0, 0)
            .divisor(1, 1)
            .populateVbo(2, tntUvCoords, GL_STATIC_DRAW)
            .populateVao(2, 2, GL_FLOAT, 0, 0)
            .unbindVbo(2)
            .unbindVao();

    public TntRendererInstanced(Class<? extends Entity> instancedClass) {
        super(instancedClass);
    }

    @Override
    public void render() {
        if (offsets.isEmpty()) return;

        float[] array = ClientUtils.translateFloatList(offsets);
        tnt.populateVbo(1, array, GL_STREAM_DRAW);
        tnt.renderInstanced(GL_QUADS, 0, 24, array.length / 3);
        offsets.clear();
    }

    @Override
    public boolean fillOffsets(Entity entity, float partialTicks) {
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
}