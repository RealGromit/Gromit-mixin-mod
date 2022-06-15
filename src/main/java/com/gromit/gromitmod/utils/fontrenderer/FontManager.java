package com.gromit.gromitmod.utils.fontrenderer;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class FontManager {

    @Getter private static TTFFontRenderer normalSize;
    @Getter private static TTFFontRenderer titleSize;
    @Getter private static TTFFontRenderer biggerTitleSize;
    @Getter private static TTFFontRenderer tinySize;

    private TTFFontRenderer createNewFont(String fontFile, int size, ExecutorService executor, ConcurrentLinkedQueue<TextureData> textureQueue) throws IOException, FontFormatException {
        final InputStream fontInStream = getClass().getResourceAsStream("/assets/astrix/font/" + fontFile + ".otf");

        Font createdFont = Font.createFont(0, fontInStream);
        createdFont = createdFont.deriveFont(Font.PLAIN, (float) size);
        return new TTFFontRenderer(executor, textureQueue, createdFont);
    }

    public FontManager() {
        final ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue<>();
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            normalSize = createNewFont("font", 19, executor, textureQueue);
            titleSize = createNewFont("font", 26, executor, textureQueue);
            biggerTitleSize = createNewFont("font", 35, executor, textureQueue);
            tinySize = createNewFont("font", 1, executor, textureQueue);
        }
        catch (Exception e) {e.printStackTrace(); e.getCause();}

        executor.shutdown();
        while(!executor.isTerminated()) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!textureQueue.isEmpty()) {
                final TextureData textureData = textureQueue.poll();
                GlStateManager.bindTexture(textureData.getTextureId());
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, GL11.GL_ZERO, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), GL11.GL_ZERO, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());
            }
        }

        Runtime.getRuntime().gc();
        System.gc();
    }
}
