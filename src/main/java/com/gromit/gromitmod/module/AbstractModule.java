package com.gromit.gromitmod.module;

import net.minecraftforge.common.MinecraftForge;

import java.io.Serializable;

public abstract class AbstractModule implements Serializable {

    private boolean state = false;

    public void register() {
        MinecraftForge.EVENT_BUS.register(this);
        onEnable();
        state = true;
    }

    public void unregister() {
        MinecraftForge.EVENT_BUS.unregister(this);
        onDisable();
        state = false;
    }

    public void updateAfterDeserialization() {}

    protected void onEnable() {}

    protected void onDisable() {}

    public boolean isState() {return state;}

    public void setState(boolean state) {this.state = state;}
}
