package com.gromit.gromitmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class ClientUtils {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

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

    public static float[] translateFloatList(List<Float> entities) {
        float[] array = new float[entities.size()];

        for (int i = 0; i < entities.size();) {
            array[i] = (float) (entities.get(i++) - renderManager.viewerPosX - 0.5);
            array[i] = (float) (entities.get(i++) - renderManager.viewerPosY);
            array[i] = (float) (entities.get(i++) - renderManager.viewerPosZ - 0.5);
        }
        return array;
    }

    public static float[] list2FloatArray(List<Float> floats) {
        float[] array = new float[floats.size()];

        int index = 0;
        for (float number : floats) {
            array[index++] = number;
        }
        return array;
    }
}