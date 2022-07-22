package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.utils.MathUtils;
import com.gromit.gromitmod.utils.moderngl.GlobalRenderManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockSand;
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

    /**
     * @author Gromit
     */
    @Overwrite
    public boolean renderEntitySimple(Entity entity, float partialTicks) {
        if (entity instanceof EntityTNTPrimed) {
            GlobalRenderManager.instance.tntRenderer.queueRender(MathUtils.entity2Coords(entity, partialTicks));
            return false;
        } else if (entity instanceof EntityFallingBlock) {
            EntityFallingBlock fallingBlock = (EntityFallingBlock) entity;
            Block block = fallingBlock.getBlock().getBlock();

            if (block instanceof BlockSand && fallingBlock.getBlock().getValue(BlockSand.VARIANT).equals(BlockSand.EnumType.SAND)) {
                GlobalRenderManager.instance.sandRenderer.queueRender(MathUtils.entity2Coords(fallingBlock, partialTicks));
                return false;
            } else if (block instanceof BlockSand && fallingBlock.getBlock().getValue(BlockSand.VARIANT).equals(BlockSand.EnumType.RED_SAND)) {
                GlobalRenderManager.instance.redSandRenderer.queueRender(MathUtils.entity2Coords(fallingBlock, partialTicks));
                return false;
            } else if (block instanceof BlockGravel) {
                GlobalRenderManager.instance.gravelRenderer.queueRender(MathUtils.entity2Coords(fallingBlock, partialTicks));
                return false;
            }
        } return renderEntityStatic(entity, partialTicks, false);
    }
}