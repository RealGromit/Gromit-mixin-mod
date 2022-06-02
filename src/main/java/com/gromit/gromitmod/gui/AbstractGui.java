package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.GromitButton;
import com.gromit.gromitmod.gui.button.listener.*;
import com.gromit.gromitmod.utils.GlobalSaver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractGui extends GuiScreen {

    protected static final GromitMod gromitMod = GromitMod.getInstance();
    protected static final Minecraft minecraft = gromitMod.getMinecraft();
    protected double guiScale;

    private int eventButton;
    private long lastMouseEvent;
    private GromitButton selectedButton;

    protected final List<GromitButton> buttonList = new ArrayList<>();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.scale(guiScale, guiScale, guiScale);

        mouseX /= guiScale;
        mouseY /= guiScale;
        for (GromitButton button : buttonList) {
            if (!button.isHovering() && isMouseOver(button, mouseX, mouseY)) {
                button.setHovering(true);
                for (ButtonListener buttonListener : button.getButtonListeners()) {
                    if (buttonListener instanceof StartHoverListener) ((StartHoverListener) buttonListener).onStartHover(button);
                }
            }
            else if (button.isHovering() && !isMouseOver(button, mouseX, mouseY)) {
                button.setHovering(false);
                for (ButtonListener buttonListener : button.getButtonListeners()) {
                    if (buttonListener instanceof EndHoverListener) ((EndHoverListener) buttonListener).onEndHover(button);
                }
            }
            if (button.isHovering()) {
                for (ButtonListener buttonListener : button.getButtonListeners()) {
                    if (buttonListener instanceof HoverListener) ((HoverListener) buttonListener).onHover(button);
                }
            }
            break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            GlobalSaver.setLastAbstractGuiScreen(this);
            mc.displayGuiScreen(null);
            if (mc.currentScreen == null) {
                mc.setIngameFocus();
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (GromitButton button : buttonList) {
            if (!button.isHovering()) continue;
            if (mouseButton == 0) {
                button.setState(!button.isState());
                for (ButtonListener buttonListener : button.getButtonListeners()) {
                    if (buttonListener instanceof ClickListener) ((ClickListener) buttonListener).onClick(button);
                    if (button.isState() && buttonListener instanceof ClickEnableListener) ((ClickEnableListener) buttonListener).onClickEnable(button);
                    else if (!button.isState() && buttonListener instanceof ClickDisableListener) ((ClickDisableListener) buttonListener).onClickDisable(button);
                }
                selectedButton = button;
            }
            else if (mouseButton == 1) {
                for (ButtonListener buttonListener : button.getButtonListeners()) {
                    if (buttonListener instanceof RightClickListener) ((RightClickListener) buttonListener).onRightClick(button);
                }
            }
            else if (mouseButton == 2) {
                for (ButtonListener buttonListener : button.getButtonListeners()) {
                    if (buttonListener instanceof MiddleClickListener) ((MiddleClickListener) buttonListener).onMiddleClick(button);
                }
            }
            break;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (selectedButton != null && state == 0) {
            for (ButtonListener buttonListener : selectedButton.getButtonListeners()) {
                if (buttonListener instanceof ReleaseListener) ((ReleaseListener) buttonListener).onRelease(selectedButton);
            }
            selectedButton = null;
        }
    }

    @Override
    public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
        super.setWorldAndResolution(minecraft, width, height);
    }

    // setGuiSize(int width, int height) {}
    @Override
    public void func_183500_a(int width, int height) {
        super.func_183500_a(width, height);
    }

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
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
    }

    @Override
    public void onResize(Minecraft minecraft, int width, int height) {
        super.onResize(minecraft, width, height);
    }

    protected boolean isMouseOver(GromitButton gromitButton, int mouseX, int mouseY) {
        return mouseX >= gromitButton.getX() && mouseY >= gromitButton.getY() && mouseX < gromitButton.getX() + gromitButton.getWidth() && mouseY < gromitButton.getY() + gromitButton.getHeight();
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
    public void initGui() {guiScale = width / 480.0;}

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
