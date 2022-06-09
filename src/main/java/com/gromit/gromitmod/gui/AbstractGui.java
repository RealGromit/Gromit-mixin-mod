package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.AbstractButton;
import com.gromit.gromitmod.utils.GlobalSaver;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractGui extends GuiScreen {

    protected static final GromitMod gromitMod = GromitMod.getInstance();
    protected static final Minecraft minecraft = gromitMod.getMinecraft();
    protected double guiScale;
    protected int scroll;

    private int eventButton;
    private long lastMouseEvent;
    private AbstractButton selectedButton;

    @Getter protected final CopyOnWriteArrayList<AbstractButton> buttonList = new CopyOnWriteArrayList<>();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.scale(guiScale, guiScale, guiScale);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            if (this instanceof MainGui) GlobalSaver.setLastAbstractGuiScreen(this);
            mc.displayGuiScreen(null);
            if (mc.currentScreen == null) {
                mc.setIngameFocus();
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (AbstractButton button : buttonList) {
            if (!button.isEnabled()) continue;
            if (button.mousePressed(mouseButton, mouseX, mouseY)) {
                selectedButton = button;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (selectedButton != null && state == 0) {
            if (selectedButton.isEnabled()) selectedButton.mouseReleased(mouseX, mouseY);
            selectedButton = null;
        }
    }

    @Override
    public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
        super.setWorldAndResolution(minecraft, width, height);
    }

    // setGuiSize(int width, int height) {}
    @Override
    public void func_183500_a(int width, int height) {}

    @Override
    public void handleInput() throws IOException {
        super.handleInput();
    }

    @Override
    public void handleMouseInput() {
        int i = (int) (Mouse.getEventX() * width / mc.displayWidth / guiScale);
        int j = (int) ((height - Mouse.getEventY() * height / mc.displayHeight - 1) / guiScale);
        int k = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            eventButton = k;
            lastMouseEvent = Minecraft.getSystemTime();
            mouseClicked(i, j, eventButton);
        } else if (k != -1) {
            eventButton = -1;
            mouseReleased(i, j, k);
        } else if (eventButton != -1 && lastMouseEvent > 0L) {
            long l = Minecraft.getSystemTime() - lastMouseEvent;
            mouseClickMove(i, j, eventButton, l);
        }
        if (Mouse.getEventDWheel() != 0) {
            scroll = Mouse.getEventDWheel() / 60;
            for (AbstractButton button : buttonList) {
                button.mouseScrolled(scroll);
            }
        }
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
    }

    @Override
    public void onResize(Minecraft minecraft, int width, int height) {
        setWorldAndResolution(minecraft, width, height);
    }

    @Override
    protected void renderToolTip(ItemStack stack, int x, int y) {}

    @Override
    protected void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {}

    @Override
    protected void drawHoveringText(List<String> textLines, int x, int y) {}

    @Override
    protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {}

    @Override
    protected void handleComponentHover(IChatComponent component, int x, int y) {}

    @Override
    protected void setText(String newChatText, boolean shouldOverwrite) {}

    @Override
    protected boolean handleComponentClick(IChatComponent component) {return false;}

    @Override
    public void sendChatMessage(String msg) {}

    @Override
    public void sendChatMessage(String msg, boolean addToChat) {}

    @Override
    protected void actionPerformed(GuiButton button) {}

    @Override
    public void initGui() {
        guiScale = width / 480.0;
        buttonList.clear();
    }

    @Override
    public void updateScreen() {}

    @Override
    public void onGuiClosed() {}

    @Override
    public void drawDefaultBackground() {}

    @Override
    public void drawWorldBackground(int tint) {}

    @Override
    public void drawBackground(int tint) {}

    @Override
    public boolean doesGuiPauseGame() {return false;}

    @Override
    public void confirmClicked(boolean result, int id) {}

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}

    @Override
    public int hashCode() {return Objects.hashCode(buttonList);}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractGui)) return false;
        return buttonList.equals(((AbstractGui) obj).buttonList) && getClass().getSimpleName().equals(obj.getClass().getSimpleName());
    }

    @Override
    public String toString() {return getClass().getSimpleName();}
}
