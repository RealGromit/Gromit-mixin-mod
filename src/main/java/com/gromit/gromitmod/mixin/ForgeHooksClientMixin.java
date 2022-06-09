package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.gui.AbstractGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ForgeHooksClient.class)
public class ForgeHooksClientMixin {

    /**
     * @author Gromit
     */
    @Overwrite(remap = false)
    public static void drawScreen(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {
        if (!MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.DrawScreenEvent.Pre(screen, mouseX, mouseY, partialTicks))) {
            if (screen instanceof AbstractGui) {
                double guiScale = screen.width / 480.0;
                mouseX /= guiScale;
                mouseY /= guiScale;
            }
            screen.drawScreen(mouseX, mouseY, partialTicks);
        }

        MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.DrawScreenEvent.Post(screen, mouseX, mouseY, partialTicks));
    }
}