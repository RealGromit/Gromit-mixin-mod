package com.gromit.gromitmod;

import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.listener.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "GromitMod", version = "1.0")
public class GromitMod {

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final MainGui mainGui = new MainGui(this);

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientTickEvent(this));
    }

    public Minecraft getMinecraft() {return minecraft;}

    public MainGui getMainGui() {
        return mainGui;
    }
}
