package com.gromit.gromitmod.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {

    private static final Tessellator tessellator;
    private static final WorldRenderer worldRenderer;
    private static final double PI = Math.PI;

    static {
        tessellator = Tessellator.getInstance();
        worldRenderer = tessellator.getWorldRenderer();
    }

    public static void drawRectangle(double x1, double y1, double x2, double y2, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(x1, y1, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x1, y1 + y2, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x1 + x2, y1 + y2, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x1 + x2, y1, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawLine(double x1, double y1, double x2, double y2, float lineWidth, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(x1, y1, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x1 + x2, y1 + y2, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawTextureColor(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(x1, y1, 0).tex(x3, y3).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x1, y1 + y2, 0).tex(x3, y3 + y4).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x1 + x2, y1 + y2, 0).tex(x3 + x4, y3 + y4).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x1 + x2, y1, 0).tex(x3 + x4, y3).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }

    public static void drawTexture(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(x1, y1, 0).tex(x3, y3).endVertex();
        worldRenderer.pos(x1, y1 + y2, 0).tex(x3, y3 + y4).endVertex();
        worldRenderer.pos(x1 + x2, y1 + y2, 0).tex(x3 + x4, y3 + y4).endVertex();
        worldRenderer.pos(x1 + x2, y1, 0).tex(x3 + x4, y3).endVertex();
        tessellator.draw();
    }

    public static void drawCircleOutline(double x1, double y1, double radius, float pointSize, int iterations, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_POINT_SMOOTH);
        glPointSize(pointSize);
        worldRenderer.begin(GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < iterations; i++) {
            double angle = PI * 2 * i / iterations;
            worldRenderer.pos(x1 + Math.cos(angle) * radius, y1 + Math.sin(angle) * radius, 0).color(red, green, blue, alpha).endVertex();
        }
        tessellator.draw();
        glDisable(GL_POINT_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCircleFilled(double x1, double y1, double radius, int iterations, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.color((float) (color >> 16 & 255) / 255f, (float) (color >> 8 & 255) / 255f, (float) (color & 255) / 255f, (float) (color >> 24 & 255) / 255f);
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_POLYGON_SMOOTH);
        worldRenderer.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        for (int i = 0; i < iterations; i++) {
            double angle = PI * 2 * i / iterations;
            worldRenderer.pos(x1 + Math.sin(angle) * radius, y1 + Math.cos(angle) * radius, 0).endVertex();
        }
        tessellator.draw();
        glDisable(GL_POLYGON_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

    }

    public static void drawRoundedThinRectangle(int x1, int y1, int width, int height, int iterations, int color) {
        int radius = height / 2;
        int circleDrawPoint = y1 + radius;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color((float) (color >> 16 & 255) / 255f, (float) (color >> 8 & 255) / 255f, (float) (color & 255) / 255f, (float) (color >> 24 & 255) / 255f);
        glEnable(GL_POLYGON_SMOOTH);
        worldRenderer.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x1 + radius, y1, 0).endVertex();
        for (int i = 0; i < iterations; i++) {
            double angle = PI * i / iterations;
            worldRenderer.pos(x1 + width - radius + Math.sin(angle) * radius, circleDrawPoint + Math.cos(angle) * radius, 0).endVertex();
        }
        tessellator.draw();
        worldRenderer.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x1 + width - radius, y1 + height, 0).endVertex();
        for (int i = 0; i < iterations; i++) {
            double angle = - PI * i / iterations;
            worldRenderer.pos(x1 + radius + Math.sin(angle) * radius, circleDrawPoint - Math.cos(angle) * radius, 0).endVertex();
        }
        tessellator.draw();
        glDisable(GL_POLYGON_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}