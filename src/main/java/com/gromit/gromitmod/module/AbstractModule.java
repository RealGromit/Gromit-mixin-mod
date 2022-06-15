package com.gromit.gromitmod.module;

import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.common.MinecraftForge;

public abstract class AbstractModule {

    @Getter @Setter private boolean state;

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

    // Needed to reassign transient/static fields and lambda
    public void updateAfterDeserialization() {}

    protected void onEnable() {}

    protected void onDisable() {}
}
