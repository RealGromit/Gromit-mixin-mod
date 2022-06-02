package com.gromit.gromitmod;

import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.InputHandler;
import com.gromit.gromitmod.handler.JsonHandler;
import com.gromit.gromitmod.listener.ChunkMapper;
import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.listener.PlayerInteractEvent;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = "GromitMod", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class GromitMod {

    private static GromitMod instance;
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final String moduleFolder = minecraft.mcDataDir.getPath() + "/mods/gromitmod/modules";
    private final String persistFolder = minecraft.mcDataDir.getPath() + "/mods/gromitmod/persist";
    private final String schematicFolder = minecraft.mcDataDir.getPath() + "/schematics";
    private final List<Integer> buttonIds = new ArrayList<>(200);

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        instance = this;
        createFolder();
        FontUtil.bootstrap();
        new ChunkMapper(minecraft);
        initButtonIds();
        JsonHandler jsonHandler = new JsonHandler();
        new GuiHandler(this);
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

    private void initButtonIds() {
        for (int i = 0; i < 200; i++) buttonIds.add(i);
    }

    public int getNewButtonId() {
        int buttonId = buttonIds.get(0);
        buttonIds.remove(0);
        return buttonId;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public String getModuleFolder() {
        return moduleFolder;
    }

    public String getPersistFolder() {
        return persistFolder;
    }

    public String getSchematicFolder() {
        return schematicFolder;
    }

    public static GromitMod getInstance() {
        return instance;
    }
}