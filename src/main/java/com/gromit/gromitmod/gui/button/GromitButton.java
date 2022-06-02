package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.listener.ButtonListener;
import com.gromit.gromitmod.utils.fontrenderer.CustomFontRenderer;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.HashSet;

public class GromitButton {

    protected static final GromitMod gromitMod = GromitMod.getInstance();
    protected static final Minecraft minecraft = gromitMod.getMinecraft();

    @Getter @Setter protected int x;
    @Getter @Setter protected int y;
    @Getter protected int width;
    @Getter protected int height;
    @Getter protected String buttonText;
    @Getter @Setter protected boolean hovering;
    @Getter @Setter protected boolean state;
    @Getter protected int color;
    @Getter protected CustomFontRenderer fontRenderer;

    @Getter private final HashSet<ButtonListener> buttonListeners = new HashSet<>();

    public GromitButton(CustomFontRenderer fontRenderer, int x, int y) {
        this.fontRenderer = fontRenderer;
        this.x = x;
        this.y = y;
        if (fontRenderer.equals(FontUtil.normal)) height = 4;
        else if (fontRenderer.equals(FontUtil.title)) height = 6;
    }

    public void drawButton(int mouseX, int mouseY) {
        fontRenderer.drawString(buttonText, x, y, color);
    }

    public GromitButton setWidth(int width) {
        this.width = width;
        return this;
    }

    public GromitButton setHeight(int height) {
        this.height = height;
        return this;
    }

    public GromitButton setButtonText(String buttonText) {
        this.buttonText = buttonText;
        width = (int) (fontRenderer.getStringWidth(buttonText) / 2);
        return this;
    }

    public GromitButton setColor(int color) {
        this.color = color;
        return this;
    }

    public GromitButton addButtonListener(ButtonListener buttonListener) {
        buttonListeners.add(buttonListener);
        return this;
    }
}
