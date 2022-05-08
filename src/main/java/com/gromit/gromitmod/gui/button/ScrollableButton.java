package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class ScrollableButton extends AbstractBaseButton {

    private int scroll;

    public ScrollableButton(int buttonId, int x, int y, int height, String displayString, OnEnable onEnable, OnDisable onDisable) {
        super(buttonId, x, y, (int) (FontUtil.normal.getStringWidth(displayString) / 2), height, displayString, onEnable, onDisable);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= getGuiScale();
        mouseY /= getGuiScale();
        hovered = mouseX >= xPosition && mouseY >= yPosition - scroll && mouseX < xPosition + width && mouseY < yPosition - scroll + height;
        if (isState()) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
        } else if (isMouseOver()) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, ColorUtils.getRGB());
        } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2 - scroll, Color.WHITE.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= getGuiScale();
        mouseY /= getGuiScale();
        if (mouseX >= xPosition && mouseY >= yPosition - scroll && mouseX < xPosition + width && mouseY < yPosition - scroll + height) {
            if (isState()) getOnDisable().onDisable();
            else getOnEnable().onEnable();
            return true;
        }
        return false;
    }

    public void setScrollOffset(int scroll) {
        this.scroll = scroll;
    }
}
