package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.saver.PersistCheckbox;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class CheckboxButton extends AbstractBaseButton {
    private final PersistCheckbox persistCheckbox;

    public CheckboxButton(int buttonId, int x, int y, int width, int height, OnEnable onEnable, OnDisable onDisable, PersistCheckbox persistCheckbox) {
        super(buttonId, x, y, width, height, "", onEnable, onDisable);
        this.persistCheckbox = persistCheckbox;
    }

    public CheckboxButton(int buttonId, int x, int y, int width, int height, PersistCheckbox persistCheckbox) {
        super(buttonId, x, y, width, height, "");
        this.persistCheckbox = persistCheckbox;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        if (persistCheckbox.isState()) {
            RenderUtils.drawLine(xPosition, yPosition, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition + height, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition, 0, height, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition + width, yPosition, 0, height, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition, width, height, 2, 255, 255, 255, persistCheckbox.getAlpha());
            RenderUtils.drawLine(xPosition, yPosition + height, width, -height, 2, 255, 255, 255, persistCheckbox.getAlpha());
            if (persistCheckbox.getAlpha() != 255) persistCheckbox.setAlpha(persistCheckbox.getAlpha() + 5);
        } else {
            RenderUtils.drawLine(xPosition, yPosition, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition + height, width, 0, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition, yPosition, 0, height, 2, 255, 255, 255, 255);
            RenderUtils.drawLine(xPosition + width, yPosition, 0, height, 2, 255, 255, 255, 255);
            if (persistCheckbox.getAlpha() != 0) {
                RenderUtils.drawLine(xPosition, yPosition, width, height, 2, 255, 255, 255, persistCheckbox.getAlpha());
                RenderUtils.drawLine(xPosition, yPosition + height, width, -height, 2, 255, 255, 255, persistCheckbox.getAlpha());
                persistCheckbox.setAlpha(persistCheckbox.getAlpha() - 5);
            }
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (hovered) {
            if (persistCheckbox.isState() && onDisable != null) onDisable.onDisable(this);
            else if (!persistCheckbox.isState() && onEnable != null) onEnable.onEnable(this);
            persistCheckbox.setState(!persistCheckbox.isState());
            return true;
        } return false;
    }

    public PersistCheckbox getPersistCheckbox() {
        return persistCheckbox;
    }
}
