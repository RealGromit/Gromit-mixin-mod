package com.gromit.gromitmod;

import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "GromitMod", version = "1.0")
public class GromitMod {

    public static GromitMod INSTANCE;
    private final Minecraft minecraft = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        INSTANCE = this;
        FontUtil.bootstrap();
        MinecraftForge.EVENT_BUS.register(new ClientTickEvent(this));
    }

    public Minecraft getMinecraft() {return minecraft;}
}