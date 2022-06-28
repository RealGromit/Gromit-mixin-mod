package com.gromit.gromitmod.entityrenderer;

import com.gromit.gromitmod.module.fps.FallingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FallingBlockEntity extends Render<EntityFallingBlock> {

    private final FallingBlock fallingBlockModule = FallingBlock.getInstance();

    public FallingBlockEntity(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityFallingBlock fallingBlock, double x, double y, double z, float entityYaw, float partialTicks) {
        if (fallingBlock.getBlock() != null) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            IBlockState lvt_10_1_ = fallingBlock.getBlock();
            Block lvt_11_1_ = lvt_10_1_.getBlock();
            BlockPos lvt_12_1_ = new BlockPos(fallingBlock);
            World lvt_13_1_ = fallingBlock.getWorldObj();
            if (lvt_10_1_ != lvt_13_1_.getBlockState(lvt_12_1_) && lvt_11_1_.getRenderType() != -1) {
                if (lvt_11_1_.getRenderType() == 3) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)x, (float)y, (float)z);
                    GlStateManager.disableLighting();
                    boolean xRay = false;
                    if (fallingBlockModule.fallingBlockXRay.isState() && fallingBlockModule.isState()) {
                        GlStateManager.disableDepth();
                        GlStateManager.depthMask(false);
                        xRay = true;
                    }
                    Tessellator lvt_14_1_ = Tessellator.getInstance();
                    WorldRenderer lvt_15_1_ = lvt_14_1_.getWorldRenderer();
                    lvt_15_1_.begin(7, DefaultVertexFormats.BLOCK);
                    int lvt_16_1_ = lvt_12_1_.getX();
                    int lvt_17_1_ = lvt_12_1_.getY();
                    int lvt_18_1_ = lvt_12_1_.getZ();
                    lvt_15_1_.setTranslation((double)((float)(-lvt_16_1_) - 0.5F), (double)(-lvt_17_1_), (double)((float)(-lvt_18_1_) - 0.5F));
                    BlockRendererDispatcher lvt_19_1_ = Minecraft.getMinecraft().getBlockRendererDispatcher();
                    IBakedModel lvt_20_1_ = lvt_19_1_.getModelFromBlockState(lvt_10_1_, lvt_13_1_, (BlockPos)null);
                    lvt_19_1_.getBlockModelRenderer().renderModel(lvt_13_1_, lvt_20_1_, lvt_10_1_, lvt_12_1_, lvt_15_1_, false);
                    lvt_15_1_.setTranslation(0.0, 0.0, 0.0);
                    lvt_14_1_.draw();
                    if (xRay) {
                        GlStateManager.depthMask(true);
                        GlStateManager.enableDepth();
                    }
                    GlStateManager.enableLighting();
                    GlStateManager.popMatrix();
                    super.doRender(fallingBlock, x, y, z, entityYaw, partialTicks);
                }
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFallingBlock entityFallingBlock) {
        return TextureMap.locationBlocksTexture;
    }
}
