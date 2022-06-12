package com.gromit.gromitmod.gui.button;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.listener.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

public abstract class AbstractButton<T> {

    protected static final GromitMod gromitMod = GromitMod.getInstance();
    protected static final Minecraft minecraft = gromitMod.getMinecraft();

    @Getter @Setter protected float x;
    @Getter @Setter protected float y;
    @Getter protected float width;
    @Getter protected float height;
    @Getter protected boolean state;
    @Getter @Setter protected transient boolean hovering;
    @Getter protected boolean enabled = true;

    protected transient DrawListener drawListener;
    protected transient ClickListener clickListener;
    protected transient RightClickListener rightClickListener;
    protected transient MiddleClickListener middleClickListener;
    protected transient ClickDisableListener clickDisableListener;
    protected transient ClickEnableListener clickEnableListener;
    protected transient ReleaseListener releaseListener;
    protected transient HoverListener hoverListener;
    protected transient EndHoverListener endHoverListener;
    protected transient StartHoverListener startHoverListener;
    protected transient DraggedListener draggedListener;
    protected transient ScrollListener scrollListener;
    protected transient StateEnableListener stateEnableListener;
    protected transient StateDisableListener stateDisableListener;

    public AbstractButton(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawButton(float mouseX, float mouseY) {
        if (drawListener != null) drawListener.onListen(this);

        if (!hovering && isMouseOver(mouseX, mouseY)) {
            hovering = true;
            if (startHoverListener != null) startHoverListener.onListen(this);
        }
        else if (hovering && !isMouseOver(mouseX, mouseY)) {
            hovering = false;
            if (endHoverListener != null) endHoverListener.onListen(this);
        }
        if (hovering && !state) if (hoverListener != null) hoverListener.onListen(this);
        mouseDragged(mouseX, mouseY);
    }

    public boolean mousePressed(float mouseButton, float mouseX, float mouseY) {
        if (!hovering) return false;
        if (mouseButton == 0) {
            if (state) setState(false);
            else setState(true);
            if (state && clickEnableListener != null) clickEnableListener.onListen(this);
            else if (!state && clickDisableListener != null) clickDisableListener.onListen(this);
            if (clickListener != null) clickListener.onListen(this);
            return true;
        }
        else if (mouseButton == 1 && rightClickListener != null) {
            rightClickListener.onListen(this);
            return true;
        }
        else if (mouseButton == 2 && middleClickListener != null) {
            middleClickListener.onListen(this);
            return true;
        }
        return false;
    }

    public void mouseDragged(float mouseX, float mouseY) {
        if (draggedListener != null) draggedListener.onListen(this, mouseX, mouseY);
    }

    public void mouseReleased(float mouseX, float mouseY) {
        if (releaseListener != null) releaseListener.onListen(this, mouseX, mouseY);
    }

    public void mouseScrolled(float scroll) {
        if (scrollListener != null) scrollListener.onListen(this, scroll);
    }

    public T setWidth(float width) {
        this.width = width;
        return (T) this;
    }

    public T setHeight(float height) {
        this.height = height;
        return (T) this;
    }

    public T setEnabled(boolean enabled) {
        this.enabled = enabled;
        return (T) this;
    }

    public void setState(boolean state) {
        this.state = state;
        if (state && stateEnableListener != null) stateEnableListener.onListen(this);
        else if (!state && stateDisableListener != null) stateDisableListener.onListen(this);
    }

    public T addButtonListener(ButtonListener buttonListener) {
        if (buttonListener instanceof DrawListener) drawListener = (DrawListener) buttonListener;
        else if (buttonListener instanceof ClickListener) clickListener = (ClickListener) buttonListener;
        else if (buttonListener instanceof RightClickListener) rightClickListener = (RightClickListener) buttonListener;
        else if (buttonListener instanceof MiddleClickListener) middleClickListener = (MiddleClickListener) buttonListener;
        else if (buttonListener instanceof ClickDisableListener) clickDisableListener = (ClickDisableListener) buttonListener;
        else if (buttonListener instanceof ClickEnableListener) clickEnableListener = (ClickEnableListener) buttonListener;
        else if (buttonListener instanceof ReleaseListener) releaseListener = (ReleaseListener) buttonListener;
        else if (buttonListener instanceof HoverListener) hoverListener = (HoverListener) buttonListener;
        else if (buttonListener instanceof EndHoverListener) endHoverListener = (EndHoverListener) buttonListener;
        else if (buttonListener instanceof StartHoverListener) startHoverListener = (StartHoverListener) buttonListener;
        else if (buttonListener instanceof DraggedListener) draggedListener = (DraggedListener) buttonListener;
        else if (buttonListener instanceof ScrollListener) scrollListener = (ScrollListener) buttonListener;
        else if (buttonListener instanceof StateEnableListener) stateEnableListener = (StateEnableListener) buttonListener;
        else if (buttonListener instanceof StateDisableListener) stateDisableListener = (StateDisableListener) buttonListener;
        return (T) this;
    }

    protected boolean isMouseOver(float mouseX, float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
