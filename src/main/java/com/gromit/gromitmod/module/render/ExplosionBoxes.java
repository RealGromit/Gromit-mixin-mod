package com.gromit.gromitmod.module.render;

import com.gromit.gromitmod.event.network.OutboundPacket;
import com.gromit.gromitmod.module.AbstractModule;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExplosionBoxes extends AbstractModule {

    public ExplosionBoxes() {
        register();
    }

    @SubscribeEvent
    public void onPacketSend(OutboundPacket event) {

    }
}
