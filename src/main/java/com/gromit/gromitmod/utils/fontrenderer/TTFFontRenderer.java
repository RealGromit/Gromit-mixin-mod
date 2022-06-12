package com.gromit.gromitmod.utils.fontrenderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public class TTFFontRenderer {

    private static TTFFontRenderer instance;
    private final boolean antiAlias;
    private final Font font;
    private boolean fractionalMetrics;
    private CharacterData[] regularData;
    private final int[] colorCodes;

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font) {
        this(executorService, textureQueue, font, 256);
        instance = this;
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final int characterCount) {
        this(executorService, textureQueue, font, characterCount, true);
        instance = this;
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final boolean antiAlias) {
        this(executorService, textureQueue, font, 256, antiAlias);
        instance = this;
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final int characterCount, final boolean antiAlias) {
        instance = this;
        this.colorCodes = new int[32];
        this.font = font;
        this.fractionalMetrics = true;
        this.antiAlias = antiAlias;
        final int[] regularTexturesIds = new int[characterCount];

        for (int i = 0; i < characterCount; ++i) {
            regularTexturesIds[i] = GL11.glGenTextures();
        }

        executorService.execute(() -> this.regularData = this.setup(new CharacterData[characterCount], regularTexturesIds, textureQueue, 0));
    }

    public static TTFFontRenderer getInstance() {
        return instance;
    }

    private CharacterData[] setup(final CharacterData[] characterData, final int[] texturesIds, final ConcurrentLinkedQueue<TextureData> textureQueue, final int type) {
        final Font derivedFont = this.font.deriveFont(type);
        final BufferedImage utilityImage = new BufferedImage(1, 1, 2);
        final Graphics2D utilityGraphics = (Graphics2D)utilityImage.getGraphics();
        utilityGraphics.setFont(derivedFont);
        final FontMetrics fontMetrics = utilityGraphics.getFontMetrics();

        for (int index = 0; index < characterData.length; ++index) {
            final char character = (char)index;
            final Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", utilityGraphics);
            final float width = (float)characterBounds.getWidth() + 8.0f;
            final float height = (float)characterBounds.getHeight();
            final BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_float_int(width), MathHelper.ceiling_float_int(height), 2);
            final Graphics2D graphics = (Graphics2D)characterImage.getGraphics();
            graphics.setFont(derivedFont);
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
            graphics.setColor(Color.WHITE);

            if (this.antiAlias) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            }

            graphics.drawString(character + "", 4, fontMetrics.getAscent());
            final int textureId = texturesIds[index];
            this.createTexture(textureId, characterImage, textureQueue);
            characterData[index] = new CharacterData(character, (float)characterImage.getWidth(), (float)characterImage.getHeight(), textureId);
        }

        return characterData;
    }

    private void createTexture(final int textureId, final BufferedImage image, final ConcurrentLinkedQueue<TextureData> textureQueue) {
        final int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                final int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 0xFF));
                buffer.put((byte)(pixel >> 8 & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)(pixel >> 24 & 0xFF));
            }
        }

        buffer.flip();
        textureQueue.add(new TextureData(textureId, image.getWidth(), image.getHeight(), buffer));
    }

    public int drawString(final String text, final float x, final float y, final int color) {
        return this.renderString(text, x, y, color, false);
    }

    public void drawCenteredString(final String text, final float x, final float y, final int color) {
        final float width = this.getWidth(text) / 2.0f;
        this.renderString(text, x - width, y, color, false);
    }

    public void drawCenteredStringWithShadow(final String text, final float x, final float y, final int color) {
        final float width = this.getWidth(text) / 2.0f;
        GL11.glTranslated(0.5, 0.5, 0.0);
        this.renderString(text, x - width, y, color, true);
        GL11.glTranslated(-0.5, -0.5, 0.0);
        this.renderString(text, x - width, y, color, false);
    }

    public void drawStringWithShadow(final String text, final float x, final float y, final int color) {
        GL11.glTranslated(0.5, 0.5, 0.0);
        this.renderString(text, x, y, color, true);
        GL11.glTranslated(-0.5, -0.5, 0.0);
        this.renderString(text, x, y, color, false);
    }

    private int renderString(final String text, float x, float y, final int color, final boolean shadow) {
        GL11.glPushMatrix();
        GlStateManager.scale(0.25, 0.25, 1.0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        x -= 2.0f;
        y -= 2.0f;
        x += 0.5f;
        y += 0.5f;
        x *= 4.0f;
        y *= 4.0f;
        x += 0.6f;
        y += 1.8f;

        GL11.glColor4d((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
        final int length = text.length();
        for (int i = 0; i < length; ++i) {
            char character = text.charAt(i);

            this.drawChar(character, regularData, x, y);
            final CharacterData charData = regularData[character];
            x += charData.width - 8.0f;
        }

        GL11.glPopMatrix();
        GlStateManager.resetColor();
        GlStateManager.disableBlend();
        GlStateManager.bindTexture(0);
        return (int) x;
    }

    public float getWidth(final String text) {
        float width = 0;
        CharacterData[] characterData = this.regularData;
        int length = text.length();

        for (int i = 0; i < length; ++i) {
            final char character = text.charAt(i);

            final CharacterData charData = characterData[character];
            width += (charData.width - 9.3f) / 2f;
        }
        return (width + 2f) / 2;
    }

    public float getHeight(final String text) {
        float height = 0;
        CharacterData[] characterData = this.regularData;
        int length = text.length();

        for (int i = 0; i < length; ++i) {
            final char character = text.charAt(i);

            final CharacterData charData = characterData[character];
            height = Math.max(height, charData.height);
        }
        return height / 2.0f - 2.0f;
    }

    private void drawChar(final char character, final CharacterData[] characterData, final float x, final float y) {
        final CharacterData charData = characterData[character];
        charData.bind();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2d(x, y + charData.height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2d((x + charData.width), y + charData.height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2d(x + charData.width, y);
        GL11.glEnd();
    }

    class CharacterData {
        public char character;
        public float width;
        public float height;
        private int textureId;

        public CharacterData(final char character, final float width, final float height, final int textureId) {
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        }
    }
}
