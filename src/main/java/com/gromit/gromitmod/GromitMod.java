package com.gromit.gromitmod;

import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.ModuleHandler;
import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.network.NetworkManager;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "GromitMod", version = "1.0")
public class GromitMod {

    private static GromitMod instance;
    private final Minecraft minecraft = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        instance = this;
        FontUtil.bootstrap();
        new GuiHandler(this);
        new ModuleHandler(this);
        new ClientTickEvent(this);
        new NetworkManager();
    }

    public Minecraft getMinecraft() {return minecraft;}

    public static GromitMod getInstance() {
        return instance;
    }
}