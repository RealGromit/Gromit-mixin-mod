package com.gromit.gromitmod.handler.buttonhandler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

public class MainGuiButtons {

    private static final Minecraft minecraft = GromitMod.INSTANCE.getMinecraft();

    private static final TextButton modules = new TextButton(0, 0, 0, (int) (FontUtil.normal.getStringWidth("Modules") / 1.9), 4, "Modules", 1,
            () -> {
                if (Saver.getLastModuleScreen() != null) minecraft.displayGuiScreen(Saver.getLastModuleScreen());
                else minecraft.displayGuiScreen(GuiHandler.getModuleGui());
            });

    private static final TextButton settings = new TextButton(1, 0, 0, (int) ((int) FontUtil.normal.getStringWidth("Settings") / 1.9), 4, "Settings", 1,
            () -> minecraft.displayGuiScreen(GuiHandler.getSettingsGui()));

    public static TextButton getModules() {
        return modules;
    }

    public static TextButton getSettings() {
        return settings;
    }
}
