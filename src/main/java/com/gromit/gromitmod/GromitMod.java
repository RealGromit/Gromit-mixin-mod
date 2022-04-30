package com.gromit.gromitmod;

import com.gromit.gromitmod.handler.ModuleHandler;
import com.gromit.gromitmod.listener.ClientTickEvent;
import com.gromit.gromitmod.handler.ButtonHandler;
import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "GromitMod", version = "1.0")
public class GromitMod {

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private ModuleHandler moduleHandler;
    private ButtonHandler buttonHandler;
    private GuiHandler guiHandler;
    public static GromitMod INSTANCE;

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        INSTANCE = this;
        FontUtil.bootstrap();
        moduleHandler = new ModuleHandler(this);
        buttonHandler = new ButtonHandler(this);
        guiHandler = new GuiHandler(this);
        MinecraftForge.EVENT_BUS.register(new ClientTickEvent(this));
    }

    public Minecraft getMinecraft() {return minecraft;}

    public GuiHandler getGuiManager() {
        return guiHandler;
    }

    public ButtonHandler getButtonWrapper() {
        return buttonHandler;
    }

    public ModuleHandler getModuleHandler() {
        return moduleHandler;
    }
}