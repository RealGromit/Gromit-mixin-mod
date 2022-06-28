package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.gui.button.listener.EndHoverListener;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.TTFFontRenderer;
import lombok.Getter;

import java.awt.Color;

public class TextButton extends AbstractButton<TextButton> {

    @Getter protected String buttonText;
    @Getter protected int color = Color.WHITE.getRGB();
    @Getter protected TTFFontRenderer fontRenderer;

    public TextButton(TTFFontRenderer fontRenderer, int x, int y) {
        super(x, y);
        this.fontRenderer = fontRenderer;
        if (fontRenderer.equals(normal)) height = 16;
        else if (fontRenderer.equals(title)) height = 24;
        addButtonListener((EndHoverListener) button -> ((TextButton) button).setColor(Color.WHITE.getRGB()));
    }

    @Override
    public void drawButton(int mouseX, int mouseY) {
        if (!enabled) return;
        super.drawButton(mouseX, mouseY);

        if (state) color = ColorUtils.getRGB();
        else if (hovering) color = ColorUtils.getRGB();
        fontRenderer.drawString(buttonText, x, y, color);
    }

    public TextButton setButtonText(String buttonText) {
        this.buttonText = buttonText;
        width = fontRenderer.getWidth(buttonText);
        return this;
    }

    public TextButton setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public void setState(boolean state) {
        super.setState(state);

        if (!state) color = Color.WHITE.getRGB();
    }
}
