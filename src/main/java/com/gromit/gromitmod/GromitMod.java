package com.gromit.gromitmod;

import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.ModuleHandler;
import com.gromit.gromitmod.listener.ChunkMapper;
import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.listener.PlayerInteractEvent;
import com.gromit.gromitmod.network.NetworkManager;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.io.File;

@Mod(modid = "GromitMod", version = "1.0")
public class GromitMod {

    private static GromitMod instance;
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final String jsonFolder = minecraft.mcDataDir.getPath() + "/mods/gromitmod";

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        instance = this;
        createFolder();
        FontUtil.bootstrap();
        new ChunkMapper(minecraft);
        ModuleHandler moduleHandler = new ModuleHandler(this);
        new GuiHandler(this);
        new ClientTickEvent(this);
        new PlayerInteractEvent(minecraft);
        new NetworkManager();
        Runtime.getRuntime().addShutdownHook(new Thread(moduleHandler::writeModules));
    }

    private void createFolder() {
        File folder = new File(jsonFolder);
        if (folder.exists()) return;
        folder.mkdir();
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public String getJsonFolder() {
        return jsonFolder;
    }

    public static GromitMod getInstance() {
        return instance;
    }
}