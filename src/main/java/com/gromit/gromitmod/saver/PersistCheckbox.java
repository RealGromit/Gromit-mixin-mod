package com.gromit.gromitmod.saver;

import java.io.Serializable;

public class PersistCheckbox implements Serializable {

    private boolean state;
    private int alpha;

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}
