package com.gromit.gromitmod.utils.moderngl;

import net.minecraft.util.ResourceLocation;

public class Shaders {

    public static final Shader worldUvRenderer = new Shader(
            new ResourceLocation("astrix", "shaders/worldUvInstancedVertex.glsl"),
            new ResourceLocation("astrix", "shaders/worldUvInstancedFragment.glsl"),
            "projection");

    public static final Shader guiRoundedRectangle = new Shader(
            new ResourceLocation("astrix", "shaders/guiRoundedRectangleVertex.glsl"),
            new ResourceLocation("astrix", "shaders/guiRoundedRectangleFragment.glsl"),
            "projection");
}
