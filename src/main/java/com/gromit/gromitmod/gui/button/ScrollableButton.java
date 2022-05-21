package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.Color;
import java.util.function.Consumer;

public class ScrollableButton extends AbstractBaseButton {

    private int scroll;

    public ScrollableButton(int buttonId, int height, String displayString, Consumer<AbstractBaseButton> onEnable, Consumer<AbstractBaseButton> onDisable) {
        super(buttonId, (int) (FontUtil.normal.getStringWidth(displayString) / 2), height, displayString, onEnable, onDisable);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition - scroll && mouseX < xPosition + width && mouseY < yPosition - scroll + height;
        if (state) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
        } else if (hovered) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
        } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, Color.WHITE.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (hovered) {
            if (state) {
                onDisable.accept(this);
                state = false;
            }
            else {
                onEnable.accept(this);
                state = true;
            }
            return true;
        } return false;
    }

    public void setScrollOffset(int scroll) {
        this.scroll = scroll;
    }
}
