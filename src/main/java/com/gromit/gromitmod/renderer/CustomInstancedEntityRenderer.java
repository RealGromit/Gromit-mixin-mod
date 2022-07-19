package com.gromit.gromitmod.renderer;

import com.gromit.gromitmod.utils.moderngl.FloatArrayBuilder;
import com.gromit.gromitmod.utils.moderngl.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CustomInstancedEntityRenderer {

    public static List<CustomInstancedEntityRenderer> customRenderers = new ArrayList<>();
    public static HashMap<Class<? extends Entity>, List<CustomInstancedEntityRenderer>> instancedMap = new HashMap<>();
    protected final TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
    protected List<Float> offsets = new ArrayList<>();
    protected final float[] cubeCoords = FloatArrayBuilder.AABB2Floats(0, 0, 0, 1, 1, 1);
    public static final Shader instanceShader = new Shader(new ResourceLocation("astrix", "shaders/vertexinstanced.glsl"),
            new ResourceLocation("astrix", "shaders/fragmentinstanced.glsl"),
            "projection");

    public CustomInstancedEntityRenderer(Class<? extends Entity> instancedClass) {
        registerCustomEntityRenderer(instancedClass, this);
    }

    public abstract void render();

    public abstract boolean fillOffsets(Entity entity, float partialTicks);

    private void registerCustomEntityRenderer(Class<? extends Entity> instancedClass, CustomInstancedEntityRenderer customInstancedEntityRenderer) {
        customRenderers.add(customInstancedEntityRenderer);
        instancedMap.putIfAbsent(instancedClass, new ArrayList<>());
        instancedMap.get(instancedClass).add(customInstancedEntityRenderer);
    }
}
