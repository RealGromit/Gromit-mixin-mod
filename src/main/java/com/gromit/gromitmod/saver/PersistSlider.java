package com.gromit.gromitmod.saver;

import java.io.Serializable;

public class PersistSlider implements Serializable {

    private int currentProgress;

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }
}
