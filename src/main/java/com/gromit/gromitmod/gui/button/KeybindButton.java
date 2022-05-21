package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class KeybindButton extends AbstractBaseButton {

    private boolean detectingInput = false;

    public KeybindButton(int buttonId, int height, String displayString) {
        super(buttonId, (int) (FontUtil.normal.getStringWidth(displayString) / 2), height, displayString);
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
        if (!isDetectingInput() && hovered) {
            displayString = "...";
            width = (int) (FontUtil.normal.getStringWidth(displayString) / 2);
            setDetectingInput(true);
            return true;
        } return false;
    }

    public void updateKeybind(String displayString, int width) {
        this.displayString = displayString;
        this.width = width;
        setDetectingInput(false);
    }

    public boolean isDetectingInput() {
        return detectingInput;
    }

    public void setDetectingInput(boolean detectingInput) {
        this.detectingInput = detectingInput;
    }
}
