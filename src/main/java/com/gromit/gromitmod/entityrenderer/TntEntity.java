package com.gromit.gromitmod.entityrenderer;

import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.module.fps.Tnt;
import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import com.gromit.gromitmod.utils.fontrenderer.TTFFontRenderer;
import com.gromit.gromitmod.utils.moderngl.BufferObjectLoader;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.HashSet;

import static org.lwjgl.opengl.GL11.*;

public class TntEntity extends Render<EntityTNTPrimed> {

    @Getter private static TntEntity instance;
    private final Tnt tntModule = Tnt.getInstance();
    private final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
    private final TTFFontRenderer normal = FontManager.getNormalSize();
    public final HashSet<EntityTNTPrimed> blacklistedTnt = new HashSet<>();
    public static BufferObjectLoader tnt;

    public TntEntity(RenderManager renderManager) {
        super(renderManager);
        instance = this;
        shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityTNTPrimed tnt, double x, double y, double z, float entityYaw, float partialTicks) {
        //if (tntModule.isState()) {
        //    if (tntModule.tntFuseLabel.isState()) renderFuseLabel(tnt.fuse, x, y, z);
        //    GlStateManager.pushMatrix();
        //    GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
        //    float lvt_11_2_;
        //    if (tntModule.tntSwell.isState()) {
        //        if ((float)tnt.fuse - partialTicks + 1.0F < 10.0F) {
        //            lvt_11_2_ = 1.0F - ((float)tnt.fuse - partialTicks + 1.0F) / 10.0F;
        //            lvt_11_2_ = MathHelper.clamp_float(lvt_11_2_, 0.0F, 1.0F);
        //            lvt_11_2_ *= lvt_11_2_;
        //            lvt_11_2_ *= lvt_11_2_;
        //            float lvt_12_1_ = 1.0F + lvt_11_2_ * 0.3F;
        //            GlStateManager.scale(lvt_12_1_, lvt_12_1_, lvt_12_1_);
        //        }
        //    }
//
        //    this.bindEntityTexture(tnt);
        //    GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        //    if (tntModule.tntXRay.isState()) {
        //        GlStateManager.disableDepth();
        //        GlStateManager.depthMask(false);
        //        blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), tnt.getBrightness(partialTicks));
        //        GlStateManager.depthMask(true);
        //        GlStateManager.enableDepth();
        //    } else blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), tnt.getBrightness(partialTicks));
        //    GlStateManager.translate(0.0F, 0.0F, 1.0F);
        //    if (tntModule.tntFlash.isState()) {
        //        if (tnt.fuse / 5 % 2 == 0) {
        //            lvt_11_2_ = (1.0F - ((float)tnt.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
        //            GlStateManager.disableTexture2D();
        //            GlStateManager.disableLighting();
        //            GlStateManager.enableBlend();
        //            GlStateManager.blendFunc(770, 772);
        //            GlStateManager.color(1.0F, 1.0F, 1.0F, lvt_11_2_);
        //            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
        //            GlStateManager.enablePolygonOffset();
        //            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
        //            GlStateManager.doPolygonOffset(0.0F, 0.0F);
        //            GlStateManager.disablePolygonOffset();
        //            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        //            GlStateManager.disableBlend();
        //            GlStateManager.enableLighting();
        //            GlStateManager.enableTexture2D();
        //        }
        //    }
        //    GlStateManager.popMatrix();
        //} else {
        //    GlStateManager.pushMatrix();
        //    GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
        //    float lvt_11_2_;
        //    if ((float)tnt.fuse - partialTicks + 1.0F < 10.0F) {
        //        lvt_11_2_ = 1.0F - ((float)tnt.fuse - partialTicks + 1.0F) / 10.0F;
        //        lvt_11_2_ = MathHelper.clamp_float(lvt_11_2_, 0.0F, 1.0F);
        //        lvt_11_2_ *= lvt_11_2_;
        //        lvt_11_2_ *= lvt_11_2_;
        //        float lvt_12_1_ = 1.0F + lvt_11_2_ * 0.3F;
        //        GlStateManager.scale(lvt_12_1_, lvt_12_1_, lvt_12_1_);
        //    }
//
        //    lvt_11_2_ = (1.0F - ((float)tnt.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
        //    this.bindEntityTexture(tnt);
        //    GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        //    blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), tnt.getBrightness(partialTicks));
        //    GlStateManager.translate(0.0F, 0.0F, 1.0F);
        //    if (tnt.fuse / 5 % 2 == 0) {
        //        GlStateManager.disableTexture2D();
        //        GlStateManager.disableLighting();
        //        GlStateManager.enableBlend();
        //        GlStateManager.blendFunc(770, 772);
        //        GlStateManager.color(1.0F, 1.0F, 1.0F, lvt_11_2_);
        //        GlStateManager.doPolygonOffset(-3.0F, -3.0F);
        //        GlStateManager.enablePolygonOffset();
        //        blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
        //        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        //        GlStateManager.disablePolygonOffset();
        //        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        //        GlStateManager.disableBlend();
        //        GlStateManager.enableLighting();
        //        GlStateManager.enableTexture2D();
        //    }
//
        //    GlStateManager.popMatrix();
        //    super.doRender(tnt, x, y, z, entityYaw, partialTicks);
        //}
    }

    @Override
    public boolean shouldRender(EntityTNTPrimed tnt, ICamera camera, double x, double y, double z) {
        if (tntModule.isState() && tntModule.tntMinimal.isState()) {
            for (EntityTNTPrimed entity : ClientTickEvent.cachedTntList) {
                if (entity.equals(tnt)) continue;
                if (blacklistedTnt.contains(entity)) continue;

                if (ClientUtils.samePos(entity, tnt)) {
                    blacklistedTnt.add(tnt);
                    return false;
                }
            }
        }
        return super.shouldRender(tnt, camera, x, y, z);
    }

    private void renderFuseLabel(int fuse, double x, double y, double z) {
        glPushMatrix();
        glTranslated(x, y + 1.3, z);
        glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        glScalef(0.05f, 0.05f, 0.05f);
        glRotatef(180, 0, 0, 1);
        glDisable(GL_LIGHTING);
        if (tntModule.tntXRay.isState()) {
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
        }
        String fuseString = String.valueOf(fuse);
        normal.drawString(fuseString, -normal.getWidth(fuseString) / 2.2f, 0, Color.WHITE.getRGB());
        if (tntModule.tntXRay.isState()) {
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
        }
        glEnable(GL_LIGHTING);
        glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTNTPrimed entityTNTPrimed) {
        return TextureMap.locationBlocksTexture;
    }
}
