package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.saver.PersistBoolean;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class ScrollableButton extends AbstractBaseButton {

    private int scroll;
    private final PersistBoolean persistBoolean;

    public ScrollableButton(int buttonId, int x, int y, int height, String displayString, OnEnable onEnable, OnDisable onDisable, PersistBoolean persistBoolean) {
        super(buttonId, x, y, (int) (FontUtil.normal.getStringWidth(displayString) / 2), height, displayString, onEnable, onDisable);
        this.persistBoolean = persistBoolean;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition - scroll && mouseX < xPosition + width && mouseY < yPosition - scroll + height;
        if (persistBoolean.isState()) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
        } else if (hovered) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
        } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, Color.WHITE.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (hovered) {
            if (persistBoolean.isState()) {
                onDisable.onDisable(this);
                persistBoolean.setState(false);
            }
            else {
                onEnable.onEnable(this);
                persistBoolean.setState(true);
            }
            return true;
        }
        return false;
    }

    public void setScrollOffset(int scroll) {
        this.scroll = scroll;
    }

    public PersistBoolean getPersistBoolean() {
        return persistBoolean;
    }
}
