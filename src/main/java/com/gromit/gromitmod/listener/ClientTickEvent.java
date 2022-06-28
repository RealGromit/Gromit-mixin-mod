package com.gromit.gromitmod.listener;

import com.gromit.gromitmod.entityrenderer.TntEntity;
import com.gromit.gromitmod.gui.AbstractGui;
import com.gromit.gromitmod.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class ClientTickEvent {

    private final TntEntity tntEntityRenderer = TntEntity.getInstance();
    private final Minecraft minecraft = Minecraft.getMinecraft();
    public static List<Entity> cachedEntityList;
    public static List<EntityTNTPrimed> cachedTntList = new ArrayList<>();
    public static List<EntityFallingBlock> cachedFallingBlockList = new ArrayList<>();

    public ClientTickEvent() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        ColorUtils.refreshColors();
        AbstractGui.tickCounter++;
        if (event.phase == TickEvent.Phase.END) return;

        cachedEntityList = minecraft.theWorld.getLoadedEntityList();
        cachedTntList.clear();
        cachedFallingBlockList.clear();
        tntEntityRenderer.blacklistedTnt.clear();
        for (Entity entity : cachedEntityList) {
            if (entity instanceof EntityTNTPrimed) cachedTntList.add((EntityTNTPrimed) entity);
            if (entity instanceof EntityFallingBlock) cachedFallingBlockList.add((EntityFallingBlock) entity);
        }
    }
}