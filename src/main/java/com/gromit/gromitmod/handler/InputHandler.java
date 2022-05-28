package com.gromit.gromitmod.handler;


import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class InputHandler {

    private static final KeyBinding KEY_BINDING_LOAD = new KeyBinding("Click Gui", 56, "Gromitmod");
    public static KeyBinding[] KEY_BINDINGS;
    private final Minecraft minecraft;

    public InputHandler(Minecraft minecraft) {
        MinecraftForge.EVENT_BUS.register(this);
        this.minecraft = minecraft;

        ClientRegistry.registerKeyBinding(KEY_BINDING_LOAD);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent event) {
        if (minecraft.currentScreen == null) {
            if (KEY_BINDING_LOAD.isPressed()) {
                if (Saver.getLastScreen() != null) minecraft.displayGuiScreen(Saver.getLastScreen());
                else minecraft.displayGuiScreen(MainGui.getInstance());
            }
        }
    }

    static {
        KEY_BINDINGS = new KeyBinding[]{KEY_BINDING_LOAD};
    }
}