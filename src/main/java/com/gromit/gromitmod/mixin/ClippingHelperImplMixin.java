package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.utils.moderngl.WorldMatrices;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.FloatBuffer;

@Mixin(ClippingHelperImpl.class)
public abstract class ClippingHelperImplMixin extends ClippingHelper {

    @Shadow private FloatBuffer projectionMatrixBuffer;
    @Shadow private FloatBuffer modelviewMatrixBuffer;
    @Shadow private FloatBuffer field_78564_h;
    @Shadow protected abstract void normalize(float[] p_normalize_1_);

    /**
     * @author Gromit
     */
    @Overwrite
    public void init() {
        projectionMatrixBuffer.clear();
        modelviewMatrixBuffer.clear();
        field_78564_h.clear();
        GlStateManager.getFloat(2983, projectionMatrixBuffer);
        GlStateManager.getFloat(2982, modelviewMatrixBuffer);
        float[] lvt_1_1_ = projectionMatrix;
        float[] lvt_2_1_ = modelviewMatrix;
        projectionMatrixBuffer.flip().limit(16);
        projectionMatrixBuffer.get(lvt_1_1_);
        modelviewMatrixBuffer.flip().limit(16);
        modelviewMatrixBuffer.get(lvt_2_1_);
        clippingMatrix[0] = lvt_2_1_[0] * lvt_1_1_[0] + lvt_2_1_[1] * lvt_1_1_[4] + lvt_2_1_[2] * lvt_1_1_[8] + lvt_2_1_[3] * lvt_1_1_[12];
        clippingMatrix[1] = lvt_2_1_[0] * lvt_1_1_[1] + lvt_2_1_[1] * lvt_1_1_[5] + lvt_2_1_[2] * lvt_1_1_[9] + lvt_2_1_[3] * lvt_1_1_[13];
        clippingMatrix[2] = lvt_2_1_[0] * lvt_1_1_[2] + lvt_2_1_[1] * lvt_1_1_[6] + lvt_2_1_[2] * lvt_1_1_[10] + lvt_2_1_[3] * lvt_1_1_[14];
        clippingMatrix[3] = lvt_2_1_[0] * lvt_1_1_[3] + lvt_2_1_[1] * lvt_1_1_[7] + lvt_2_1_[2] * lvt_1_1_[11] + lvt_2_1_[3] * lvt_1_1_[15];
        clippingMatrix[4] = lvt_2_1_[4] * lvt_1_1_[0] + lvt_2_1_[5] * lvt_1_1_[4] + lvt_2_1_[6] * lvt_1_1_[8] + lvt_2_1_[7] * lvt_1_1_[12];
        clippingMatrix[5] = lvt_2_1_[4] * lvt_1_1_[1] + lvt_2_1_[5] * lvt_1_1_[5] + lvt_2_1_[6] * lvt_1_1_[9] + lvt_2_1_[7] * lvt_1_1_[13];
        clippingMatrix[6] = lvt_2_1_[4] * lvt_1_1_[2] + lvt_2_1_[5] * lvt_1_1_[6] + lvt_2_1_[6] * lvt_1_1_[10] + lvt_2_1_[7] * lvt_1_1_[14];
        clippingMatrix[7] = lvt_2_1_[4] * lvt_1_1_[3] + lvt_2_1_[5] * lvt_1_1_[7] + lvt_2_1_[6] * lvt_1_1_[11] + lvt_2_1_[7] * lvt_1_1_[15];
        clippingMatrix[8] = lvt_2_1_[8] * lvt_1_1_[0] + lvt_2_1_[9] * lvt_1_1_[4] + lvt_2_1_[10] * lvt_1_1_[8] + lvt_2_1_[11] * lvt_1_1_[12];
        clippingMatrix[9] = lvt_2_1_[8] * lvt_1_1_[1] + lvt_2_1_[9] * lvt_1_1_[5] + lvt_2_1_[10] * lvt_1_1_[9] + lvt_2_1_[11] * lvt_1_1_[13];
        clippingMatrix[10] = lvt_2_1_[8] * lvt_1_1_[2] + lvt_2_1_[9] * lvt_1_1_[6] + lvt_2_1_[10] * lvt_1_1_[10] + lvt_2_1_[11] * lvt_1_1_[14];
        clippingMatrix[11] = lvt_2_1_[8] * lvt_1_1_[3] + lvt_2_1_[9] * lvt_1_1_[7] + lvt_2_1_[10] * lvt_1_1_[11] + lvt_2_1_[11] * lvt_1_1_[15];
        clippingMatrix[12] = lvt_2_1_[12] * lvt_1_1_[0] + lvt_2_1_[13] * lvt_1_1_[4] + lvt_2_1_[14] * lvt_1_1_[8] + lvt_2_1_[15] * lvt_1_1_[12];
        clippingMatrix[13] = lvt_2_1_[12] * lvt_1_1_[1] + lvt_2_1_[13] * lvt_1_1_[5] + lvt_2_1_[14] * lvt_1_1_[9] + lvt_2_1_[15] * lvt_1_1_[13];
        clippingMatrix[14] = lvt_2_1_[12] * lvt_1_1_[2] + lvt_2_1_[13] * lvt_1_1_[6] + lvt_2_1_[14] * lvt_1_1_[10] + lvt_2_1_[15] * lvt_1_1_[14];
        clippingMatrix[15] = lvt_2_1_[12] * lvt_1_1_[3] + lvt_2_1_[13] * lvt_1_1_[7] + lvt_2_1_[14] * lvt_1_1_[11] + lvt_2_1_[15] * lvt_1_1_[15];
        float[] lvt_3_1_ = frustum[0];
        lvt_3_1_[0] = clippingMatrix[3] - clippingMatrix[0];
        lvt_3_1_[1] = clippingMatrix[7] - clippingMatrix[4];
        lvt_3_1_[2] = clippingMatrix[11] - clippingMatrix[8];
        lvt_3_1_[3] = clippingMatrix[15] - clippingMatrix[12];
        normalize(lvt_3_1_);
        float[] lvt_4_1_ = frustum[1];
        lvt_4_1_[0] = clippingMatrix[3] + clippingMatrix[0];
        lvt_4_1_[1] = clippingMatrix[7] + clippingMatrix[4];
        lvt_4_1_[2] = clippingMatrix[11] + clippingMatrix[8];
        lvt_4_1_[3] = clippingMatrix[15] + clippingMatrix[12];
        normalize(lvt_4_1_);
        float[] lvt_5_1_ = frustum[2];
        lvt_5_1_[0] = clippingMatrix[3] + clippingMatrix[1];
        lvt_5_1_[1] = clippingMatrix[7] + clippingMatrix[5];
        lvt_5_1_[2] = clippingMatrix[11] + clippingMatrix[9];
        lvt_5_1_[3] = clippingMatrix[15] + clippingMatrix[13];
        normalize(lvt_5_1_);
        float[] lvt_6_1_ = frustum[3];
        lvt_6_1_[0] = clippingMatrix[3] - clippingMatrix[1];
        lvt_6_1_[1] = clippingMatrix[7] - clippingMatrix[5];
        lvt_6_1_[2] = clippingMatrix[11] - clippingMatrix[9];
        lvt_6_1_[3] = clippingMatrix[15] - clippingMatrix[13];
        normalize(lvt_6_1_);
        float[] lvt_7_1_ = frustum[4];
        lvt_7_1_[0] = clippingMatrix[3] - clippingMatrix[2];
        lvt_7_1_[1] = clippingMatrix[7] - clippingMatrix[6];
        lvt_7_1_[2] = clippingMatrix[11] - clippingMatrix[10];
        lvt_7_1_[3] = clippingMatrix[15] - clippingMatrix[14];
        normalize(lvt_7_1_);
        float[] lvt_8_1_ = frustum[5];
        lvt_8_1_[0] = clippingMatrix[3] + clippingMatrix[2];
        lvt_8_1_[1] = clippingMatrix[7] + clippingMatrix[6];
        lvt_8_1_[2] = clippingMatrix[11] + clippingMatrix[10];
        lvt_8_1_[3] = clippingMatrix[15] + clippingMatrix[14];
        normalize(lvt_8_1_);
        WorldMatrices.updateMatrices(projectionMatrix, modelviewMatrix);
    }
}
