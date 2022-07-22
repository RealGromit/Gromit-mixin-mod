package com.gromit.gromitmod.renderer;

import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.moderngl.GLObject;
import com.gromit.gromitmod.utils.moderngl.GlobalRenderManager;
import com.gromit.gromitmod.utils.moderngl.Shader;
import com.gromit.gromitmod.utils.moderngl.WorldMatrices;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Renderer {

    private int objectCount;
    private final List<Float> data = new ArrayList<>();
    private final Shader shader;
    private final GLObject glObject;
    private final RenderType renderType;
    private final ShaderType shaderType;

    public Renderer(Shader shader, GLObject glObject, RenderType renderType, ShaderType shaderType) {
        this.shader = shader;
        this.glObject = glObject;
        this.renderType = renderType;
        this.shaderType = shaderType;
        GlobalRenderManager.registerRenderer(this);
    }

    public boolean render() {
        if (data.isEmpty()) return false;

        if (GlobalRenderManager.lastShader != shader) {
            glUseProgram(shader.getShaderProgram());
            glUniformMatrix4(shader.getUniform("projection"), false, shaderType == ShaderType.WORLD ? WorldMatrices.worldProjection : WorldMatrices.guiProjection);
        }

        if (renderType == RenderType.INSTANCED) {
            glObject.populateVbo(0, ClientUtils.translateFloatList(data), GL_STREAM_DRAW);
            glObject.renderInstanced(objectCount);
            data.clear();
            objectCount = 0;
            GlobalRenderManager.lastShader = shader;
            return true;
        } else if (renderType == RenderType.BATCHED) {
            glObject.populateVbo(0, ClientUtils.list2FloatArray(data), GL_STREAM_DRAW);
            glObject.render();
            data.clear();
            GlobalRenderManager.lastShader = shader;
            return true;
        } return false;
    }

    public void queueRender(float... floats) {
        for (float data : floats) this.data.add(data);
        objectCount++;
    }
}
