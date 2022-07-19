package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.renderer.CustomInstancedEntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(RenderManager.class)
public abstract class RenderManagerMixin {

    @Shadow public abstract boolean renderEntityStatic(Entity p_renderEntityStatic_1_, float p_renderEntityStatic_2_, boolean p_renderEntityStatic_3_);

    /**
     * @author Gromit
     */
    @Overwrite
    public boolean renderEntitySimple(Entity entity, float partialTicks) {
        List<CustomInstancedEntityRenderer> instancedRenderers = CustomInstancedEntityRenderer.instancedMap.get(entity.getClass());
        if (instancedRenderers != null) {
            for (CustomInstancedEntityRenderer instancedEntityRenderer : instancedRenderers) {
                if (instancedEntityRenderer.fillOffsets(entity, partialTicks)) return false;
            }
        } return renderEntityStatic(entity, partialTicks, false);
    }
}
