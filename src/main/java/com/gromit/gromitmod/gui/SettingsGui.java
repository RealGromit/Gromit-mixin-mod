package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.KeyBindButton;
import com.gromit.gromitmod.handler.Saver;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class SettingsGui extends MainGui {

    private static final KeyBindButton openGui = new KeyBindButton(6, 0, 0, (int) (FontUtil.normal.getStringWidth("Left alt") / 2), 4, "Left alt");
    private boolean test = true;

    public SettingsGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        openGui.updateButton(mainGuiPointX + 110, mainGuiPointY + 39, guiScale);
        buttonList.add(openGui);
        settings.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.normal.drawString("Open gui keybind:", mainGuiPointX + 68, mainGuiPointY + 41, Color.WHITE.getRGB());
        openGui.drawButton(minecraft, mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {settings.setState(false);}

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();

        for (GuiButton keyBindButton : buttonList) {
            if (!(keyBindButton instanceof KeyBindButton)) continue;
            if (!((KeyBindButton) keyBindButton).isDetectingKeybind()) continue;
            keyBindButton.displayString = String.valueOf(Keyboard.getKeyName(Keyboard.getEventKey()));
            keyBindButton.width = (int) (FontUtil.normal.getStringWidth(keyBindButton.displayString) / 2);
            ((KeyBindButton) keyBindButton).setDetectingKeybind(false);
            Saver.setOpenGuiButton(Keyboard.getEventKey());
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        if (Mouse.getEventButton() == 0) return;
        if (Mouse.getEventButton() == 1) return;
        if (Mouse.getEventButton() == -1) return;
        for (GuiButton keyBindButton : buttonList) {
            if (!(keyBindButton instanceof KeyBindButton)) continue;
            if (!((KeyBindButton) keyBindButton).isDetectingKeybind()) continue;
            keyBindButton.displayString = String.valueOf(Mouse.getButtonName(Mouse.getEventButton()));
            keyBindButton.width = (int) (FontUtil.normal.getStringWidth(keyBindButton.displayString) / 2);
            ((KeyBindButton) keyBindButton).setDetectingKeybind(false);
            Saver.setOpenGuiButton(Mouse.getEventButton());
        }
    }
}
