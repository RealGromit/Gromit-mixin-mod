package com.gromit.gromitmod.mixin;

import net.minecraft.block.BlockSand;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderManager.class)
public abstract class RenderManagerMixin {

    @Shadow public abstract boolean renderEntityStatic(Entity p_renderEntityStatic_1_, float p_renderEntityStatic_2_, boolean p_renderEntityStatic_3_);

    @Shadow public abstract <T extends Entity> Render<T> getEntityRenderObject(Entity p_getEntityRenderObject_1_);

    /**
     * @author Gromit
     */
    @Overwrite
    public boolean renderEntitySimple(Entity entity, float p_renderEntitySimple_2_) {
        if (entity instanceof EntityTNTPrimed) return false;
        if (entity instanceof EntityFallingBlock) {
            if (((EntityFallingBlock) entity).getBlock().getBlock() instanceof BlockSand) return false;
        }
        return this.renderEntityStatic(entity, p_renderEntitySimple_2_, false);
    }

    /**
     * @author Gromit
     */
    @Overwrite
    public boolean shouldRender(Entity entity, ICamera p_shouldRender_2_, double p_shouldRender_3_, double p_shouldRender_5_, double p_shouldRender_7_) {
        if (entity instanceof EntityTNTPrimed) return false;
        if (entity instanceof EntityFallingBlock) {
            if (((EntityFallingBlock) entity).getBlock().getBlock() instanceof BlockSand) return false;
        }
        Render<Entity> render = this.getEntityRenderObject(entity);
        return render != null && render.shouldRender(entity, p_shouldRender_2_, p_shouldRender_3_, p_shouldRender_5_, p_shouldRender_7_);
    }
}
