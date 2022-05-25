package com.gromit.gromitmod.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerInteractEvent {

    private final Minecraft minecraft;

    public PlayerInteractEvent(Minecraft minecraft) {
        MinecraftForge.EVENT_BUS.register(this);
        this.minecraft = minecraft;
    }

    @SubscribeEvent
    public void onPlayerInteract(net.minecraftforge.event.entity.player.PlayerInteractEvent event) {
        if (event.action != net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_AIR) return;
        if (!event.world.isRemote) {
            for (int i = 0; i < 100; i++) {
                event.world.spawnEntityInWorld(new EntityTNTPrimed(event.world, minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ, null));
            }
        }
    }
}
