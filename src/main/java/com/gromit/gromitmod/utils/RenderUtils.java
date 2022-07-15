package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.gui.AbstractGui;
import com.gromit.gromitmod.module.settings.Customizations;
import com.gromit.gromitmod.utils.aabb.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.moderngl.Shader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class RenderUtils {

    private static final Tessellator tessellator;
    private static final WorldRenderer worldRenderer;
    private static final float PI = (float) Math.PI;

    private static final Shader roundedRectangle = new Shader(null, new ResourceLocation("astrix", "shaders/roundedquad.glsl"),
            "position", "size", "radius", "feather", "color");

    private static final Shader circleOutline = new Shader(null, new ResourceLocation("astrix", "shaders/circleoutline.glsl"),
            "center", "radius", "linewidth", "feather", "iframe", "color", "rgb");

    private static final Shader roundedRectangleOutline = new Shader(null, new ResourceLocation("astrix", "shaders/roundedquadoutline.glsl"),
            "position", "size", "radius", "linewidth", "feather", "iframe", "color", "rgb");

    private static final Shader circleOutlineFilled = new Shader(null, new ResourceLocation("astrix", "shaders/circleoutlinefilled.glsl"),
            "position", "radius", "linewidth", "feather", "innercolor", "outercolor");

    static {
        tessellator = Tessellator.getInstance();
        worldRenderer = tessellator.getWorldRenderer();
    }

    public static void drawCircleFilledOutline(float x, float y, float radius, float lineWidth, float feather, int innerColor, int outerColor) {
        glUseProgram(circleOutlineFilled.getShaderProgram());
        glUniform2f(circleOutlineFilled.getUniform("position"), x, Display.getHeight() - y);
        glUniform1f(circleOutlineFilled.getUniform("radius"), radius);
        glUniform1f(circleOutlineFilled.getUniform("linewidth"), lineWidth);
        glUniform1f(circleOutlineFilled.getUniform("feather"), feather);
        glUniform3f(circleOutlineFilled.getUniform("innercolor"), (innerColor >> 16 & 0xFF) / 255f, (innerColor >> 8 & 0xFF) / 255f, (innerColor & 0xFF) / 255f);
        glUniform3f(circleOutlineFilled.getUniform("outercolor"), (outerColor >> 16 & 0xFF) / 255f, (outerColor >> 8 & 0xFF) / 255f, (outerColor & 0xFF) / 255f);

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);

        x -= lineWidth + feather;
        y -= lineWidth + feather;
        float diameter = 2 * (radius + feather + lineWidth);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();
        worldRenderer.pos(x, y + diameter, 0).endVertex();
        worldRenderer.pos(x + diameter, y + diameter, 0).endVertex();
        worldRenderer.pos(x + diameter, y, 0).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        glUseProgram(0);
    }

    public static void drawRoundedRectangleOutline(float x, float y, float width, float height, float radius, float lineWidth, float feather, int color, boolean rgb) {
        glUseProgram(roundedRectangleOutline.getShaderProgram());
        glUniform2f(roundedRectangleOutline.getUniform("position"), x, Display.getHeight() - y);
        glUniform2f(roundedRectangleOutline.getUniform("size"), width, height);
        glUniform1f(roundedRectangleOutline.getUniform("radius"), radius);
        glUniform1f(roundedRectangleOutline.getUniform("linewidth"), lineWidth);
        glUniform1f(roundedRectangleOutline.getUniform("iframe"), AbstractGui.tickCounter * Customizations.getInstance().outlineSpeed.currentValue);
        glUniform1f(roundedRectangleOutline.getUniform("feather"), feather);
        Shader.setColorRGBA(roundedRectangleOutline, color);
        glUniform1i(roundedRectangleOutline.getUniform("rgb"), rgb ? 1 : 0);

        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(770, 771);

        float cached = lineWidth + feather;
        x -= cached;
        y -= cached;
        width += 2 * (cached);
        height += 2 * (cached);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();
        worldRenderer.pos(x, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y, 0).endVertex();
        tessellator.draw();

        glEnable(GL_TEXTURE_2D);
        glUseProgram(0);
    }

    public static void drawCircleOutline(float centerX, float centerY, float radius, float lineWidth, float feather, int color, boolean rgb) {
        glUseProgram(circleOutline.getShaderProgram());
        glUniform2f(circleOutline.getUniform("center"), centerX, Display.getHeight() - centerY);
        glUniform1f(circleOutline.getUniform("radius"), radius);
        glUniform1f(circleOutline.getUniform("linewidth"), lineWidth);
        glUniform1f(circleOutline.getUniform("feather"), feather);
        glUniform1f(circleOutline.getUniform("iframe"), AbstractGui.tickCounter * Customizations.getInstance().circleSpeed.currentValue);
        Shader.setColorRGBA(circleOutline, color);
        glUniform1i(circleOutline.getUniform("rgb"), rgb ? 1 : 0);

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);

        centerX -= radius + lineWidth + feather;
        centerY -= radius + lineWidth + feather;
        float diameter = 2 * (radius + feather + lineWidth);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(centerX, centerY, 0).endVertex();
        worldRenderer.pos(centerX, centerY + diameter, 0).endVertex();
        worldRenderer.pos(centerX + diameter, centerY + diameter, 0).endVertex();
        worldRenderer.pos(centerX + diameter, centerY, 0).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        glUseProgram(0);
    }

    public static void drawRoundedRectangle(float x, float y, float width, float height, float feather, float radius, int color) {
        glUseProgram(roundedRectangle.getShaderProgram());
        glUniform2f(roundedRectangle.getUniform("position"), x, Display.getHeight() - y);
        glUniform2f(roundedRectangle.getUniform("size"), width, height);
        glUniform1f(roundedRectangle.getUniform("radius"), radius);
        glUniform1f(roundedRectangle.getUniform("feather"), feather);
        Shader.setColorRGBA(roundedRectangle, color);

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);

        x -= feather;
        y -= feather;
        width += 2 * feather;
        height += 2 * feather;
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();
        worldRenderer.pos(x, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y, 0).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        glUseProgram(0);
    }

    public static void drawRectangle(float x, float y, float width, float height, int color) {
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glColor(color);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x, y + height);
        glVertex2f(x + width, y + height);
        glVertex2f(x + width, y);
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public static void drawLine(float x, float y, float width, float height, float lineWidth, boolean smoothLine, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        if (smoothLine) glEnable(GL_LINE_SMOOTH);

        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(x, y, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x + width, y + height, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        if (smoothLine) glDisable(GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawTextureColor(float x, float y, float width, float height, float texX, float texY, float texWidth, float texHeight, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(x, y, 0).tex(texX, texY).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x, y + height, 0).tex(texX, texY + texHeight).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x + width, y + height, 0).tex(texX + texWidth, texY + texHeight).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x + width, y, 0).tex(texX + texWidth, texY).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        GlStateManager.disableBlend();
    }

    public static void drawTexture(float x, float y, float width, float height, float texX, float texY, float texWidth, float texHeight) {
        glColor3f(1, 1, 1);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(x, y, 0).tex(texX, texY).endVertex();
        worldRenderer.pos(x, y + height, 0).tex(texX, texY + texHeight).endVertex();
        worldRenderer.pos(x + width, y + height, 0).tex(texX + texWidth, texY + texHeight).endVertex();
        worldRenderer.pos(x + width, y, 0).tex(texX + texWidth, texY).endVertex();
        tessellator.draw();
    }

    public static void drawShadingRectangle(float x, float y, float width, float height, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glShadeModel(GL_SMOOTH);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(x, y, 0).color(255, 255, 255, 255).endVertex();
        worldRenderer.pos(x, y + height, 0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(x + width, y + height, 0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(x + width, y, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        glShadeModel(GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawShadingRectangleWidthGradient(float x, float y, float width, float height, int red, int green, int blue, int alpha, int red1, int green1, int blue1, int alpha1) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glShadeModel(GL_SMOOTH);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(x, y, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x, y + height, 0).color(red1, green1, blue1, alpha1).endVertex();
        worldRenderer.pos(x + width, y + height, 0).color(red1, green1, blue1, alpha1).endVertex();
        worldRenderer.pos(x + width, y, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        glShadeModel(GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawShadingRectangleHeightGradient(float x, float y, float width, float height, int red, int green, int blue, int alpha, int red1, int green1, int blue1, int alpha1) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glShadeModel(GL_SMOOTH);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(x, y, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x, y + height, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x + width, y + height, 0).color(red1, green1, blue1, alpha1).endVertex();
        worldRenderer.pos(x + width, y, 0).color(red1, green1, blue1, alpha1).endVertex();
        tessellator.draw();

        glShadeModel(GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawAABB(AxisAlignedBB box, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawAABBOutline(AxisAlignedBB box, float lineWidth, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        glEnable(GL_LINE_SMOOTH);

        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        glDisable(GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawPatchcrumbsLine(AxisAlignedBBTime box, float lineWidth, int red, int green, int blue, int alpha, byte renderLineDirection) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        glEnable(GL_LINE_SMOOTH);

        if (renderLineDirection == 1) {
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX, box.maxY, box.minZ + 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX, box.maxY, box.minZ + 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX, box.minY, box.minZ + 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX, box.minY, box.minZ + 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX, box.maxY, box.maxZ - 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX, box.maxY, box.maxZ - 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX, box.minY, box.maxZ - 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX, box.minY, box.maxZ - 320).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
        }
        if (renderLineDirection == 2) {
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX + 320, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX + 320, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX + 320, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.minX + 320, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX - 320, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX - 320, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX - 320, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
            worldRenderer.pos(box.maxX - 320, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
            tessellator.draw();
        }

        glDisable(GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawAABB(AxisAlignedBBTime box, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawAABBOutline(AxisAlignedBBTime box, float lineWidth, int red, int green, int blue, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        glEnable(GL_LINE_SMOOTH);

        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        glDisable(GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void glColor(int color) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
    }

    public static void glColor(int color, float alpha) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, alpha);
    }
}