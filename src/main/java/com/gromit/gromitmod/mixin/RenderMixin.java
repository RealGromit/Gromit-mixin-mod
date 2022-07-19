package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.module.fps.FallingBlock;
import com.gromit.gromitmod.module.fps.Tnt;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;

@Mixin(Render.class)
public abstract class RenderMixin {

    @Shadow @Final protected RenderManager renderManager;
    @Shadow @Final private static ResourceLocation shadowTextures;
    @Shadow protected abstract World getWorldFromRenderManager();
    @Shadow protected float shadowSize;
    @Shadow protected abstract void func_180549_a(Block p_180549_1_, double p_180549_2_, double p_180549_4_, double p_180549_4_2, BlockPos p_180549_6_, float p_180549_6_2, float p_180549_8_, double p_180549_8_2, double p_180549_9_, double p_180549_10_);
    /**
     * @author Gromit
     */
    @Overwrite
    private void renderShadow(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity instanceof EntityTNTPrimed && !Tnt.getInstance().tntDropShadow.isState() && Tnt.getInstance().isState()) return;
        if (entity instanceof EntityFallingBlock && !FallingBlock.getInstance().fallingBlockDropShadow.isState() && FallingBlock.getInstance().isState()) return;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        renderManager.renderEngine.bindTexture(shadowTextures);
        World lvt_10_1_ = getWorldFromRenderManager();
        GlStateManager.depthMask(false);
        float lvt_11_1_ = shadowSize;
        if (entity instanceof EntityLiving) {
            EntityLiving lvt_12_1_ = (EntityLiving) entity;
            lvt_11_1_ *= lvt_12_1_.getRenderSizeModifier();
            if (lvt_12_1_.isChild()) {
                lvt_11_1_ *= 0.5F;
            }
        }

        double lvt_12_2_ = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
        double lvt_14_1_ = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
        double lvt_16_1_ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
        int lvt_18_1_ = MathHelper.floor_double(lvt_12_2_ - (double) lvt_11_1_);
        int lvt_19_1_ = MathHelper.floor_double(lvt_12_2_ + (double) lvt_11_1_);
        int lvt_20_1_ = MathHelper.floor_double(lvt_14_1_ - (double) lvt_11_1_);
        int lvt_21_1_ = MathHelper.floor_double(lvt_14_1_);
        int lvt_22_1_ = MathHelper.floor_double(lvt_16_1_ - (double) lvt_11_1_);
        int lvt_23_1_ = MathHelper.floor_double(lvt_16_1_ + (double) lvt_11_1_);
        double lvt_24_1_ = x - lvt_12_2_;
        double lvt_26_1_ = y - lvt_14_1_;
        double lvt_28_1_ = z - lvt_16_1_;
        Tessellator lvt_30_1_ = Tessellator.getInstance();
        WorldRenderer lvt_31_1_ = lvt_30_1_.getWorldRenderer();
        lvt_31_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        Iterator lvt_32_1_ = BlockPos.getAllInBoxMutable(new BlockPos(lvt_18_1_, lvt_20_1_, lvt_22_1_), new BlockPos(lvt_19_1_, lvt_21_1_, lvt_23_1_)).iterator();

        while (lvt_32_1_.hasNext()) {
            BlockPos lvt_33_1_ = (BlockPos) lvt_32_1_.next();
            Block lvt_34_1_ = lvt_10_1_.getBlockState(lvt_33_1_.down()).getBlock();
            if (lvt_34_1_.getRenderType() != -1 && lvt_10_1_.getLightFromNeighbors(lvt_33_1_) > 3) {
                func_180549_a(lvt_34_1_, x, y, z, lvt_33_1_, entityYaw, lvt_11_1_, lvt_24_1_, lvt_26_1_, lvt_28_1_);
            }
        }

        lvt_30_1_.draw();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }
}
