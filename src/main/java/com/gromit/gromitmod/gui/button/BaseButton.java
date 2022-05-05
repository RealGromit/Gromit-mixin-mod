package com.gromit.gromitmod.gui.button;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public abstract class BaseButton extends GuiButton {

    private double guiScale;
    private boolean state = false;
    private OnEnable onEnable;
    private OnDisable onDisable;

    public BaseButton(int buttonId, int x, int y, int width, int height, String displayString, OnEnable onEnable, OnDisable onDisable) {
        super(buttonId, x, y, width, height, displayString);
        this.onEnable = onEnable;
        this.onDisable = onDisable;
    }

    public BaseButton(int buttonId, int x, int y, int width, int height, String displayString, OnEnable onEnable) {
        super(buttonId, x, y, width, height, displayString);
        this.onEnable = onEnable;
    }

    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {}

    public void updateButton(int x1, int y1, double guiScale) {
        this.guiScale = guiScale;
        xPosition = x1;
        yPosition = y1;
    }

    public double getGuiScale() {
        return guiScale;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public OnEnable getOnEnable() {
        return onEnable;
    }

    public OnDisable getOnDisable() {
        return onDisable;
    }

    public interface OnEnable {
        void onEnable();
    }

    public interface OnDisable {
        void onDisable();
    }
}
