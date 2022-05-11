package com.gromit.gromitmod.listener;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
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
        if (minecraft.pointedEntity != null) minecraft.thePlayer.sendChatMessage(minecraft.pointedEntity.getName());
        if (Keyboard.isKeyDown(Saver.getOpenGuiButton()) || Mouse.isButtonDown(Saver.getOpenGuiButton())) {
            if (minecraft.currentScreen == null && Saver.getLastScreen() != null) minecraft.displayGuiScreen(Saver.getLastScreen());
            else if (minecraft.currentScreen == null) minecraft.displayGuiScreen(MainGui.getInstance());
        }
    }
}