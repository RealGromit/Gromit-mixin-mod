package com.gromit.gromitmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ClientUtils {

    public static void addClientMessage(Minecraft minecraft, String message) {
        minecraft.thePlayer.addChatMessage(new ChatComponentText("[GromitMod] " + message));
    }
}
