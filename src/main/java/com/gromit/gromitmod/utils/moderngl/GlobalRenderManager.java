package com.gromit.gromitmod.utils.moderngl;

import com.gromit.gromitmod.renderer.CustomInstancedEntityRenderer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class GlobalRenderManager {

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        glUseProgram(CustomInstancedEntityRenderer.instanceShader.getShaderProgram());
        glUniformMatrix4(CustomInstancedEntityRenderer.instanceShader.getUniform("projection"), false, WorldMatrices.finalProjection);
        for (CustomInstancedEntityRenderer instancedEntityRenderer : CustomInstancedEntityRenderer.customRenderers) {
            instancedEntityRenderer.render();
        }
        glUseProgram(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
