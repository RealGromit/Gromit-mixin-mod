package com.gromit.gromitmod;

import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.InputHandler;
import com.gromit.gromitmod.handler.JsonHandler;
import com.gromit.gromitmod.listener.ChunkMapper;
import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.listener.PlayerInteractEvent;
import com.gromit.gromitmod.network.NetworkManager;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.io.File;

@Mod(modid = "GromitMod", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class GromitMod {

    @Getter private static GromitMod instance;
    @Getter private final Minecraft minecraft = Minecraft.getMinecraft();
    @Getter private final String moduleFolder = minecraft.mcDataDir.getPath() + "/mods/gromitmod/modules";
    @Getter private final String persistFolder = minecraft.mcDataDir.getPath() + "/mods/gromitmod/persist";
    @Getter private final String schematicFolder = minecraft.mcDataDir.getPath() + "/schematics";

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        instance = this;
        createFolder();
        FontUtil.bootstrap();
        new ChunkMapper(minecraft);
        JsonHandler jsonHandler = new JsonHandler();
        new GuiHandler();
        new NetworkManager();
        new ClientTickEvent(this);
        new PlayerInteractEvent(minecraft);
        new InputHandler(minecraft);
        Runtime.getRuntime().addShutdownHook(new Thread(jsonHandler::writeModules));
    }

    private void createFolder() {
        File gromitMod = new File(minecraft.mcDataDir.getPath() + "/mods/gromitmod");
        if (gromitMod.exists()) return;
        gromitMod.mkdir();
        File folder = new File(moduleFolder);
        File folder1 = new File(persistFolder);
        folder.mkdir();
        folder1.mkdir();
    }
}