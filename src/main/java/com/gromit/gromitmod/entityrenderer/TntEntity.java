package com.gromit.gromitmod.entityrenderer;

import com.gromit.gromitmod.module.fps.Tnt;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import com.gromit.gromitmod.utils.fontrenderer.TTFFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TntEntity extends Render<EntityTNTPrimed> {

    private final Tnt tntModule = Tnt.getInstance();
    private final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
    private final TTFFontRenderer tiny = FontManager.getTinySize();
    private final TTFFontRenderer normal = FontManager.getNormalSize();

    public TntEntity(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityTNTPrimed tnt, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
        float lvt_11_2_;
        if (tntModule.tntSwell.isState()) {
            if ((float)tnt.fuse - partialTicks + 1.0F < 10.0F) {
                lvt_11_2_ = 1.0F - ((float)tnt.fuse - partialTicks + 1.0F) / 10.0F;
                lvt_11_2_ = MathHelper.clamp_float(lvt_11_2_, 0.0F, 1.0F);
                lvt_11_2_ *= lvt_11_2_;
                lvt_11_2_ *= lvt_11_2_;
                float lvt_12_1_ = 1.0F + lvt_11_2_ * 0.3F;
                GlStateManager.scale(lvt_12_1_, lvt_12_1_, lvt_12_1_);
            }
        }

        this.bindEntityTexture(tnt);
        GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        if (tntModule.tntXRay.isState()) {
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), tnt.getBrightness(partialTicks));
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
        } else blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), tnt.getBrightness(partialTicks));
        renderFuseLabel(tnt.fuse, 7.6f, 27, 10);
        GlStateManager.translate(0.0F, 0.0F, 1.0F);
        if (tntModule.tntFlash.isState()) {
            if (tnt.fuse / 5 % 2 == 0) {
                lvt_11_2_ = (1.0F - ((float)tnt.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
                GlStateManager.disableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 772);
                GlStateManager.color(1.0F, 1.0F, 1.0F, lvt_11_2_);
                GlStateManager.doPolygonOffset(-3.0F, -3.0F);
                GlStateManager.enablePolygonOffset();
                blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
                GlStateManager.doPolygonOffset(0.0F, 0.0F);
                GlStateManager.disablePolygonOffset();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
            }
        }

        GlStateManager.popMatrix();
        super.doRender(tnt, x, y, z, entityYaw, partialTicks);
    }

    private void renderFuseLabel(int fuse, float x, float y, float z) {
        GlStateManager.pushMatrix();
        GL11.glScalef(0.05f, 0.05f, 0.05f);
        GL11.glTranslatef(x, y, z);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glDisable(GL11.GL_LIGHTING);
        normal.drawString(String.valueOf(fuse), 0, 0, Color.WHITE.getRGB());
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTNTPrimed entityTNTPrimed) {
        return TextureMap.locationBlocksTexture;
    }
}
