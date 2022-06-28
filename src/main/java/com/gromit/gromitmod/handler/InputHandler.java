package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.gui.MainGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    private static final KeyBinding KEY_BINDING_GUI = new KeyBinding("Click Gui", 56, "Gromitmod");
    private final Minecraft minecraft;

    public InputHandler(Minecraft minecraft) {
        MinecraftForge.EVENT_BUS.register(this);
        this.minecraft = minecraft;

        List<KeyBinding> bindingList = new ArrayList<>();

        bindingList.add(KEY_BINDING_GUI);

        for (KeyBinding key : bindingList) {
            ClientRegistry.registerKeyBinding(key);
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent event) {
        if (minecraft.currentScreen == null) {
            if (KEY_BINDING_GUI.isPressed()) {
                minecraft.displayGuiScreen(MainGui.getInstance());
                //if (GlobalSaver.getLastAbstractGuiScreen() != null) minecraft.displayGuiScreen(GlobalSaver.getLastAbstractGuiScreen());
                //else minecraft.displayGuiScreen(MainGui.getInstance());
            }
        }
    }
}