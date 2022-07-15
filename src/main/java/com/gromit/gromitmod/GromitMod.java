package com.gromit.gromitmod;

import com.gromit.gromitmod.entityrenderer.FallingBlockEntity;
import com.gromit.gromitmod.entityrenderer.TntEntity;
import com.gromit.gromitmod.event.client.AfterScreenCreationEvent;
import com.gromit.gromitmod.handler.CommandHandler;
import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.InputHandler;
import com.gromit.gromitmod.handler.JsonHandler;
import com.gromit.gromitmod.listener.ChunkMapper;
import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.listener.PlayerInteractEvent;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import com.gromit.gromitmod.utils.moderngl.GlobalRenderManager;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        createFolder();
        new FontManager();
        new ChunkMapper(minecraft);
        JsonHandler jsonHandler = new JsonHandler();
        RenderingRegistry.registerEntityRenderingHandler(EntityTNTPrimed.class, new TntEntity(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlock.class, new FallingBlockEntity(Minecraft.getMinecraft().getRenderManager()));
        new GuiHandler();
        new ClientTickEvent();
        new PlayerInteractEvent(minecraft);
        new InputHandler(minecraft);
        new CommandHandler();
        Runtime.getRuntime().addShutdownHook(new Thread(jsonHandler::writeModules));
    }

    @SubscribeEvent
    public void onWindowCreation(AfterScreenCreationEvent event) {
        new GlobalRenderManager();
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