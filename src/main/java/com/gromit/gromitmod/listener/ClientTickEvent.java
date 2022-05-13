package com.gromit.gromitmod.listener;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ClientTickEvent {

    private final GromitMod gromitMod;
    private final Minecraft minecraft;

    public ClientTickEvent(GromitMod gromitMod) {
        MinecraftForge.EVENT_BUS.register(this);
        this.gromitMod = gromitMod;
        minecraft = gromitMod.getMinecraft();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ColorUtils.refreshColors();
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (Keyboard.isKeyDown(Saver.getOpenGuiButton()) || Mouse.isButtonDown(Saver.getOpenGuiButton())) {
            if (minecraft.currentScreen == null && Saver.getLastScreen() != null) minecraft.displayGuiScreen(Saver.getLastScreen());
            else if (minecraft.currentScreen == null) minecraft.displayGuiScreen(MainGui.getInstance());
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR) return;
        if (!event.world.isRemote) {
            for (int i = 0; i < 100; i++) {
                event.world.spawnEntityInWorld(new EntityTNTPrimed(event.world, minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ, null));
            }
        }
    }
}