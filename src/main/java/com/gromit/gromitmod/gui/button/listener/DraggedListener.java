package com.gromit.gromitmod.gui.button.listener;

import com.gromit.gromitmod.gui.button.AbstractButton;

public interface DraggedListener extends ButtonListener {

    void onListen(AbstractButton abstractButton, float mouseX, float mouseY);
}
