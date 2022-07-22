package com.gromit.gromitmod.utils.moderngl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

public class UvCoords {

    private static final TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
    private static final TextureAtlasSprite tntTop = textureMap.getAtlasSprite("minecraft:blocks/tnt_top");
    private static final TextureAtlasSprite tntSide = textureMap.getAtlasSprite("minecraft:blocks/tnt_side");
    private static final TextureAtlasSprite tntBottom = textureMap.getAtlasSprite("minecraft:blocks/tnt_bottom");
    public static final TextureAtlasSprite sandTexture = textureMap.getAtlasSprite("minecraft:blocks/sand");
    public static final TextureAtlasSprite gravelTexture = textureMap.getAtlasSprite("minecraft:blocks/gravel");
    public static final TextureAtlasSprite redSandTexture = textureMap.getAtlasSprite("minecraft:blocks/red_sand");

    public static final float[] tntUv = {
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

    public static float[] getUvCoords(TextureAtlasSprite sprite) {
        return new float[] {
                sprite.getMaxU(), sprite.getMaxV(),
                sprite.getMaxU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMaxV(),

                sprite.getMaxU(), sprite.getMaxV(),
                sprite.getMaxU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMaxV(),

                sprite.getMaxU(), sprite.getMaxV(),
                sprite.getMaxU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMaxV(),

                sprite.getMaxU(), sprite.getMaxV(),
                sprite.getMaxU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMaxV(),

                sprite.getMaxU(), sprite.getMaxV(),
                sprite.getMaxU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMaxV(),

                sprite.getMaxU(), sprite.getMaxV(),
                sprite.getMaxU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMinV(),
                sprite.getMinU(), sprite.getMaxV(),
        };
    }
}
