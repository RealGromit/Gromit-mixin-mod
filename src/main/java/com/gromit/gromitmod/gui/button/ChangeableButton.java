package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class ChangeableButton extends AbstractBaseButton {

    private final String disabledState;
    private final String enabledState;

    public ChangeableButton(int buttonId, int x, int y, int height, String disabledState, String enabledState, OnEnable onEnable, OnDisable onDisable) {
        super(buttonId, x, y, (int) (FontUtil.normal.getStringWidth(disabledState) / 2), height, disabledState, onEnable, onDisable);
        this.disabledState = disabledState;
        this.enabledState = enabledState;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        if (hovered) {
            FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, ColorUtils.getRGB());
        } else FontUtil.normal.drawString(displayString, xPosition + 0.5, yPosition + 2, Color.WHITE.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (hovered) {
            if (state) {
                onDisable.onDisable(this);
                displayString = disabledState;
                state = false;
            } else {
                onEnable.onEnable(this);
                displayString = enabledState;
                state = true;
            } width = (int) (FontUtil.normal.getStringWidth(displayString) / 2);
            return true;
        } return false;
    }
}
