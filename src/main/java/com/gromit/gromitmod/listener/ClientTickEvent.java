package com.gromit.gromitmod.listener;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickEvent {

    private final Minecraft minecraft;

    public ClientTickEvent(GromitMod gromitMod) {
        MinecraftForge.EVENT_BUS.register(this);
        minecraft = gromitMod.getMinecraft();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        ColorUtils.refreshColors();
    }
}