package com.gromit.gromitmod.event.network;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class InboundPacketEvent extends Event {

    private Packet<?> packet;

    public InboundPacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
