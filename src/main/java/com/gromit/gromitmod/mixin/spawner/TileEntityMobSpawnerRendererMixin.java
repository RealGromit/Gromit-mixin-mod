package com.gromit.gromitmod.mixin.spawner;

import com.gromit.gromitmod.module.fps.Spawner;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static org.lwjgl.opengl.GL11.*;

@Mixin(TileEntityMobSpawnerRenderer.class)
public abstract class TileEntityMobSpawnerRendererMixin {

    private final Spawner spawnerModule = Spawner.getInstance();
    private final ResourceLocation cheese = new ResourceLocation("astrix", "cheese.png");
    private final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

    @Shadow public static void renderMob(MobSpawnerBaseLogic p_renderMob_0_, double p_renderMob_1_, double p_renderMob_3_, double p_renderMob_3_2, float p_renderMob_5_) {}

    /**
     * @author Gromit
     */
    @Overwrite
    public void renderTileEntityAt(TileEntityMobSpawner spawner, double x, double y, double z, float partialTicks, int idk) {
        if (spawnerModule.stateCheckbox.isState()) {
            if (spawnerModule.spawnerCheese.isState()) {
                glPushMatrix();
                glTranslated(x + 0.5, y + 0.5, z + 0.5);
                glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                glRotatef(180, 0, 0, 1);
                glDisable(GL_LIGHTING);
                Minecraft.getMinecraft().getTextureManager().bindTexture(cheese);
                RenderUtils.drawTexture(-1, -0.3f, 2, 0.6f, 0, 0, 1, 1);
                glEnable(GL_LIGHTING);
                glPopMatrix();
                return;
            }

            if (spawnerModule.spawnerMobDisable.isState()) return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5f, y, z + 0.5f);
        renderMob(spawner.getSpawnerBaseLogic(), x, y, z, partialTicks);
        GlStateManager.popMatrix();
    }
}
