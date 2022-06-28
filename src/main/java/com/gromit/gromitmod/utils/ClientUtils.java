package com.gromit.gromitmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;

public class ClientUtils {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public static void addClientMessage(String message) {
        minecraft.thePlayer.addChatMessage(new ChatComponentText("[GromitMod] " + message));
    }

    public static String getClientMessage(String message) {
        return "[GromitMod] " + message;
    }

    public static double isNumeric(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {return -1;}
    }

    public static <T extends Entity> boolean samePos(T entity, T entity1) {
        return entity.posX == entity1.posX && entity.posY == entity1.posY && entity.posZ == entity1.posZ;
    }
}