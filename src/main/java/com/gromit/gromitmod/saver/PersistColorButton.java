package com.gromit.gromitmod.saver;

import java.io.Serializable;

public class PersistColorButton implements Serializable {

    private boolean state;
    private float hue = 1, saturation = 1, brightness = 1;
    private int red = 255, green, blue, alpha = 255, satRed = 255, satGreen, satBlue;

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getSatRed() {
        return satRed;
    }

    public void setSatRed(int satRed) {
        this.satRed = satRed;
    }

    public int getSatGreen() {
        return satGreen;
    }

    public void setSatGreen(int satGreen) {
        this.satGreen = satGreen;
    }

    public int getSatBlue() {
        return satBlue;
    }

    public void setSatBlue(int satBlue) {
        this.satBlue = satBlue;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
