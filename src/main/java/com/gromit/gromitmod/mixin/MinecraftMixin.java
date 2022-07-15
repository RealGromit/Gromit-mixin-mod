package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.event.client.AfterScreenCreationEvent;
import com.gromit.gromitmod.gui.AbstractGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MouseHelper;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "startGame", at = @At("RETURN"))
    private void startGame(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new AfterScreenCreationEvent());
    }
}
