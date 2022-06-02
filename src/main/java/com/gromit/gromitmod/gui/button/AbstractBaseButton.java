package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.GromitMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

import java.util.function.Consumer;

public abstract class AbstractBaseButton extends GuiButton {

    protected static final GromitMod gromitMod = GromitMod.getInstance();
    protected static final Minecraft minecraft = gromitMod.getMinecraft();
    protected transient double guiScale;
    protected boolean state;
    protected transient Consumer<AbstractBaseButton> onEnable;
    protected transient Consumer<AbstractBaseButton> onDisable;

    public AbstractBaseButton(int buttonId, int width, int height, String displayString, Consumer<AbstractBaseButton> onEnable, Consumer<AbstractBaseButton> onDisable) {
        super(buttonId, 0, 0, width, height, displayString);
        this.onEnable = onEnable;
        this.onDisable = onDisable;
    }

    public AbstractBaseButton(int buttonId, int width, int height, String displayString, Consumer<AbstractBaseButton> onEnable) {
        super(buttonId, 0, 0, width, height, displayString);
        this.onEnable = onEnable;
    }

    public AbstractBaseButton(int buttonId, int width, int height, String displayString) {
        super(buttonId, 0, 0, width, height, displayString);
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

    public Consumer<AbstractBaseButton> getOnEnable() {
        return onEnable;
    }

    public Consumer<AbstractBaseButton> getOnDisable() {
        return onDisable;
    }

    public void updateLambda(Consumer<AbstractBaseButton> onEnable, Consumer<AbstractBaseButton> onDisable) {
        this.onEnable = onEnable;
        this.onDisable = onDisable;
    }
}
