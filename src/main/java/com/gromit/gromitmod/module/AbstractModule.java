package com.gromit.gromitmod.module;

import net.minecraftforge.common.MinecraftForge;

public abstract class AbstractModule {

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

    protected void onEnable() {}

    protected void onDisable() {}

    public boolean isState() {return state;}

    public void setState(boolean state) {this.state = state;}
}
