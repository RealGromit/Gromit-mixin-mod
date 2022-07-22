package com.gromit.gromitmod.utils.moderngl;

import com.gromit.gromitmod.renderer.RenderType;
import com.gromit.gromitmod.renderer.Renderer;
import com.gromit.gromitmod.renderer.ShaderType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GlobalRenderManager {

    public static GlobalRenderManager instance;
    public final Renderer tntRenderer = new Renderer(Shaders.worldUvRenderer, GLObjects.tntRenderer, RenderType.INSTANCED, ShaderType.WORLD);
    public final Renderer sandRenderer = new Renderer(Shaders.worldUvRenderer, GLObjects.sandRenderer, RenderType.INSTANCED, ShaderType.WORLD);
    public final Renderer redSandRenderer = new Renderer(Shaders.worldUvRenderer, GLObjects.redSandRenderer, RenderType.INSTANCED, ShaderType.WORLD);
    public final Renderer gravelRenderer = new Renderer(Shaders.worldUvRenderer, GLObjects.gravelRenderer, RenderType.INSTANCED, ShaderType.WORLD);
    private static final List<Renderer> renderers = new ArrayList<>();
    public static Shader lastShader;

    public GlobalRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        boolean flag = false;
        for (Renderer renderer : renderers) if (renderer.render()) flag = true;
        if (flag) {
            glUseProgram(0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
            lastShader = null;
        }
    }

    public static void registerRenderer(Renderer renderer) {
        renderers.add(renderer);
    }
}