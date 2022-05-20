package com.gromit.gromitmod;

import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.ModuleHandler;
import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.network.NetworkManager;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.io.File;
import java.io.IOException;

@Mod(modid = "GromitMod", version = "1.0")
public class GromitMod {

    private static GromitMod instance;
    private final Minecraft minecraft = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        instance = this;
        createDatabase(minecraft.mcDataDir.getPath() + "/mods/gromitmod");
        Sqlite.createTable();
        FontUtil.bootstrap();
        ModuleHandler moduleHandler = new ModuleHandler(this);
        new GuiHandler(this);
        new ClientTickEvent(this);
        new NetworkManager();
        Runtime.getRuntime().addShutdownHook(new Thread(moduleHandler::writeModules));
    }

    private void createDatabase(String folderPath) {
        File database = new File(folderPath);
        if (database.exists()) return;
        if (!database.mkdir()) return;
        try {new File(folderPath + "/database.db").createNewFile();}
        catch (IOException e) {throw new RuntimeException(e);}
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public static GromitMod getInstance() {
        return instance;
    }
}