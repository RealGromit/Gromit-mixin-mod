package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.gui.AbstractGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MouseHelper;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow public boolean inGameHasFocus;
    @Shadow public MouseHelper mouseHelper;
    @Shadow public abstract void displayGuiScreen(GuiScreen p_displayGuiScreen_1_);
    @Shadow private int leftClickCounter;

    /**
     * @author Gromit
     */
    @Overwrite
    public void setIngameFocus() {
        if (Display.isActive() && !inGameHasFocus) {
            AbstractGui.tickCounter = 0;
            inGameHasFocus = true;
            mouseHelper.grabMouseCursor();
            displayGuiScreen(null);
            leftClickCounter = 10000;
        }
    }
}
