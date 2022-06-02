package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.KeybindButton;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class SettingsGui extends MainGui {

    private static SettingsGui instance;
    private static final GromitMod gromitMod = GromitMod.getInstance();
    private static final KeybindButton openGui = new KeybindButton(gromitMod.getNewButtonId(), 4, "Left alt");

    public SettingsGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        openGui.updateButton(mainGuiPointX + 110, mainGuiPointY + 39, guiScale);
        buttonList.add(openGui);
        settings.setState(true);
        crumbs.setState(false);
        fps.setState(false);
        fun.setState(false);
        render.setState(false);
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

        for (GuiButton button : buttonList) {
            if (!(button instanceof KeybindButton)) continue;
            KeybindButton keybindButton = (KeybindButton) button;
            if (!keybindButton.isDetectingInput()) continue;
            String displayString = Keyboard.getKeyName(Keyboard.getEventKey());
            keybindButton.updateKeybind(displayString, (int) (FontUtil.normal.getStringWidth(displayString) / 2));
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        if (Mouse.getEventButton() == 0) return;
        if (Mouse.getEventButton() == 1) return;
        if (Mouse.getEventButton() == -1) return;
        for (GuiButton button : buttonList) {
            if (!(button instanceof KeybindButton)) continue;
            KeybindButton keybindButton = (KeybindButton) button;
            if (!keybindButton.isDetectingInput()) continue;
            String displayString = Mouse.getButtonName(Mouse.getEventButton());
            keybindButton.updateKeybind(displayString, (int) (FontUtil.normal.getStringWidth(displayString) / 2));
        }
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static SettingsGui getInstance() {
        return instance;
    }
}
