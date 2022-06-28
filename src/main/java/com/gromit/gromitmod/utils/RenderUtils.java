package com.gromit.gromitmod.utils;

import com.gromit.gromitmod.gui.AbstractGui;
import com.gromit.gromitmod.utils.moderngl.Shader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class RenderUtils {

    private static final Tessellator tessellator;
    private static final WorldRenderer worldRenderer;
    private static final float PI = (float) Math.PI;
    private static final Shader roundedRectangleShader = new Shader(null, new ResourceLocation("astrix", "roundedquad.glsl"),
            "position", "size", "radius", "feather", "color");
    private static final Shader roundedRectangleOutlineRgb = new Shader(null, new ResourceLocation("astrix", "roundedquadoutlinergb.glsl"),
            "center", "size", "radius", "feather", "iframe", "color");
    private static final Shader circleOutlineRgb = new Shader(null, new ResourceLocation("astrix", "circleoutlinergb.glsl"),
            "center", "radius", "feather", "iframe", "color");
    public static final Shader xRgb = new Shader(null, new ResourceLocation("astrix", "xrgb.glsl"),
            "iframe");
    private static final Shader roundedRectangleOutline = new Shader(null, new ResourceLocation("astrix", "roundedquadoutline.glsl"),
            "position", "size", "radius", "linewidth", "feather", "color");
    private static final Shader circleFilledOutline = new Shader(null, new ResourceLocation("astrix", "filledoutlinecircle.glsl"),
            "position", "radius", "linewidth", "feather", "innercolor", "outercolor");

    static {
        tessellator = Tessellator.getInstance();
        worldRenderer = tessellator.getWorldRenderer();
    }

    public static void drawCircleFilledOutline(float x, float y, float radius, float lineWidth, float feather, int innerColor, int outerColor) {
        glUseProgram(circleFilledOutline.getShaderProgram());
        glUniform2f(circleFilledOutline.getUniform("position"), x, Display.getHeight() - y);
        glUniform1f(circleFilledOutline.getUniform("radius"), radius);
        glUniform1f(circleFilledOutline.getUniform("linewidth"), lineWidth);
        glUniform1f(circleFilledOutline.getUniform("feather"), feather);
        glUniform3f(circleFilledOutline.getUniform("innercolor"), (innerColor >> 16 & 0xFF) / 255f, (innerColor >> 8 & 0xFF) / 255f, (innerColor & 0xFF) / 255f);
        glUniform3f(circleFilledOutline.getUniform("outercolor"), (outerColor >> 16 & 0xFF) / 255f, (outerColor >> 8 & 0xFF) / 255f, (outerColor & 0xFF) / 255f);

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

    public static void drawRoundedRectangleOutline(float x, float y, float width, float height, float radius, float lineWidth, float feather, int color) {
        glUseProgram(roundedRectangleOutline.getShaderProgram());
        glUniform2f(roundedRectangleOutline.getUniform("position"), x, Display.getHeight() - y);
        glUniform2f(roundedRectangleOutline.getUniform("size"), width, height);
        glUniform1f(roundedRectangleOutline.getUniform("radius"), radius);
        glUniform1f(roundedRectangleOutline.getUniform("linewidth"), lineWidth);
        glUniform1f(roundedRectangleOutline.getUniform("feather"), feather);
        Shader.setColorRGBA(roundedRectangleOutline, color);

        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(770, 771);

        float cachedLineWidth = lineWidth / 2;
        x -= cachedLineWidth + feather;
        y -= cachedLineWidth + feather;
        width += 2 * (cachedLineWidth + feather);
        height += 2 * (cachedLineWidth + feather);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();
        worldRenderer.pos(x, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y, 0).endVertex();
        tessellator.draw();

        glEnable(GL_TEXTURE_2D);
        glUseProgram(0);
    }

    public static void drawCircleOutlineRgb(float centerX, float centerY, float radius, float feather, int color) {
        glUseProgram(circleOutlineRgb.getShaderProgram());
        glUniform2f(circleOutlineRgb.getUniform("center"), centerX, Display.getHeight() - centerY);
        glUniform1f(circleOutlineRgb.getUniform("radius"), radius);
        glUniform1f(circleOutlineRgb.getUniform("feather"), feather);
        glUniform1f(circleOutlineRgb.getUniform("iframe"), AbstractGui.tickCounter);
        Shader.setColorRGB(circleOutlineRgb, color);

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);

        float x = centerX - radius - feather;
        float y = centerY - radius - feather;
        float diameter = 2 * (radius + feather);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();
        worldRenderer.pos(x, y + diameter, 0).endVertex();
        worldRenderer.pos(x + diameter, y + diameter, 0).endVertex();
        worldRenderer.pos(x + diameter, y, 0).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        glUseProgram(0);
    }

    public static void drawRoundedRectangleOutlineRgb(float centerX, float centerY, float width, float height, float feather, float radius, int color) {
        glUseProgram(roundedRectangleOutlineRgb.getShaderProgram());
        glUniform2f(roundedRectangleOutlineRgb.getUniform("center"), centerX, Display.getHeight() - centerY);
        glUniform2f(roundedRectangleOutlineRgb.getUniform("size"), width, height);
        glUniform1f(roundedRectangleOutlineRgb.getUniform("radius"), radius);
        glUniform1f(roundedRectangleOutlineRgb.getUniform("feather"), feather);
        glUniform1f(roundedRectangleOutlineRgb.getUniform("iframe"), AbstractGui.tickCounter);
        Shader.setColorRGB(roundedRectangleOutlineRgb, color);

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);

        float x = centerX - width / 2 - feather;
        float y = centerY - height / 2 - feather;
        width += 2 * feather;
        height +=  2 *feather;
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();
        worldRenderer.pos(x, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y + height, 0).endVertex();
        worldRenderer.pos(x + width, y, 0).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        glUseProgram(0);
    }

    public static void drawRoundedRectangle1(float x, float y, float width, float height, float feather, float radius, int color) {
        glUseProgram(roundedRectangleShader.getShaderProgram());
        glUniform2f(roundedRectangleShader.getUniform("position"), x, Display.getHeight() - y);
        glUniform2f(roundedRectangleShader.getUniform("size"), width, height);
        glUniform1f(roundedRectangleShader.getUniform("radius"), radius);
        glUniform1f(roundedRectangleShader.getUniform("feather"), feather);
        Shader.setColorRGBA(roundedRectangleShader, color);

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

    public static void drawCircleOutline(float x, float y, float radius, float pointSize, float iterations, int color) {
        float cachedAngle = 2 * PI / iterations;
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_POINT_SMOOTH);
        glPointSize(pointSize);

        glColor(color);
        glBegin(GL_POINTS);
        for (float i = 0; i < iterations; i++) {
            float angle = cachedAngle * i;
            glVertex2f(x + MathHelper.cos(angle) * radius, y + MathHelper.sin(angle) * radius);
        }
        glEnd();

        glDisable(GL_POINT_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public static void drawCircleFilled(float x, float y, float radius, int color) {
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_POINT_SMOOTH);
        glPointSize(radius);

        glColor(color);
        glBegin(GL_POINTS);
        glVertex2f(x, y);
        glEnd();

        glDisable(GL_POINT_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public static void drawRoundedRectangle(float x, float y, float width, float height, int color, float alpha) {
        float halfHeight = height / 2f;
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_POINT_SMOOTH);
        glPointSize(4 * height);

        glColor(color, alpha);
        glBegin(GL_POINTS);
        for (float i = x + halfHeight; i <= x + width - halfHeight; i = i + width - 2 * halfHeight) {
            glVertex2f(i, y + halfHeight);
        }
        glEnd();
        glDisable(GL_POINT_SMOOTH);

        glBegin(GL_QUADS);
        glVertex2f(x + halfHeight, y);
        glVertex2f(x + halfHeight, y + height);
        glVertex2f(x + width - halfHeight, y + height);
        glVertex2f(x + width - halfHeight, y);
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public static void drawRoundedRectangleOutline(float x, float y, float width, float height, float pointSize, float iterations, float pointDistance, int color, float alpha) {
        float radius = height / 2;
        float circleDrawPoint = y + radius;
        float cachedAngleCalc = PI / iterations;

        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_POINT_SMOOTH);
        glPointSize(pointSize);

        glColor(color, alpha);
        glBegin(GL_POINTS);
        for (float i = x + width - radius; i > x + radius; i = i - pointDistance) glVertex2f(i, y);
        for (float i = x + radius; i < x + width - radius; i = i + pointDistance) glVertex2f(i, y + height);
        for (int i = 0; i < iterations; i++) {
            float angle = cachedAngleCalc * i;
            glVertex2f(x + width - radius + MathHelper.sin(angle) * radius, circleDrawPoint + MathHelper.cos(angle) * radius);
        }
        for (int i = 0; i < iterations; i++) {
            float angle = -cachedAngleCalc * i;
            glVertex2f(x + radius + MathHelper.sin(angle) * radius, circleDrawPoint - MathHelper.cos(angle) * radius);
        }
        glEnd();

        glDisable(GL_POINT_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
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