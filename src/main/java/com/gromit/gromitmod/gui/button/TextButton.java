package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.Color;
import java.util.function.Consumer;

public class TextButton extends AbstractBaseButton {

    public TextButton(int buttonId, int height, String displayString, Consumer<AbstractBaseButton> onEnable, Consumer<AbstractBaseButton> onDisable) {
        super(buttonId, (int) (FontUtil.normal.getStringWidth(displayString) / 2), height, displayString, onEnable, onDisable);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        if (state) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, ColorUtils.getRGB());
        } else if (hovered) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, ColorUtils.getRGB());
        } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, Color.WHITE.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (hovered) {
            if (state) {
                state = false;
                onDisable.accept(this);
            }
            else {
                state = true;
                onEnable.accept(this);
            } return true;
        } return false;
    }
}
