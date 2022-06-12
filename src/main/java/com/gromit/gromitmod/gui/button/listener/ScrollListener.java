package com.gromit.gromitmod.gui.button.listener;

import com.gromit.gromitmod.gui.button.AbstractButton;

public interface ScrollListener extends ButtonListener {

    void onListen(AbstractButton abstractButton, float scroll);
}
