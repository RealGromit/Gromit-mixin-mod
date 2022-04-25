package com.gromit.gromitmod.listener;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ClientTickEvent {

    private final GromitMod gromitMod;
    private final Minecraft minecraft;

    public ClientTickEvent(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ColorUtils.refreshColors();
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) && minecraft.currentScreen == null) minecraft.displayGuiScreen(gromitMod.getMainGui());
    }
}