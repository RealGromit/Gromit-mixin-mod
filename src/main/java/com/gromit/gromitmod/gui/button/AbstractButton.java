package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.AbstractGui;
import net.minecraft.client.Minecraft;

public abstract class AbstractButton {

    protected static final GromitMod gromitMod = GromitMod.getInstance();
    protected static final Minecraft minecraft = gromitMod.getMinecraft();

    private int x;
    private int y;
    private int width;
    private int height;
    private String buttonName;
    private boolean hovering;

    public void drawButton(int mouseX, int mouseY) {

    }

    public void buttonClicked(AbstractGui abstractGui) {

    }

    public void buttonReleased(AbstractGui abstractGui) {

    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
