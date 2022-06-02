package com.gromit.gromitmod.mixin;

import com.github.lunatrius.schematica.client.gui.control.GuiSchematicControl;
import com.github.lunatrius.schematica.client.gui.save.GuiSchematicSave;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import com.github.lunatrius.schematica.client.renderer.RenderSchematic;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.handler.client.InputHandler;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.gromit.gromitmod.gui.schematica.SchematicLoadGui;
import com.gromit.gromitmod.utils.schematic.SchematicUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InputHandler.class)
public abstract class InputHandlerMixin {

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private static KeyBinding KEY_BINDING_LOAD;
    @Shadow @Final private static KeyBinding KEY_BINDING_SAVE;
    @Shadow @Final private static KeyBinding KEY_BINDING_CONTROL;
    @Shadow @Final private static KeyBinding KEY_BINDING_LAYER_INC;
    @Shadow @Final private static KeyBinding KEY_BINDING_LAYER_DEC;
    @Shadow @Final private static KeyBinding KEY_BINDING_LAYER_TOGGLE;
    @Shadow @Final private static KeyBinding KEY_BINDING_RENDER_TOGGLE;
    @Shadow @Final private static KeyBinding KEY_BINDING_PRINTER_TOGGLE;
    @Shadow @Final private static KeyBinding KEY_BINDING_MOVE_HERE;
    @Shadow protected abstract void handlePickBlock();

    /**
     * @author Gromit
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onKeyInput(InputEvent event) {
        if (minecraft.currentScreen == null) {
            if (KEY_BINDING_LOAD.isPressed()) {
                SchematicUtils.getInstance().updateSchematics();
                minecraft.displayGuiScreen(SchematicLoadGui.getInstance());
            }

            if (KEY_BINDING_SAVE.isPressed()) {
                minecraft.displayGuiScreen(new GuiSchematicSave(minecraft.currentScreen));
            }

            if (KEY_BINDING_CONTROL.isPressed()) {
                minecraft.displayGuiScreen(new GuiSchematicControl(minecraft.currentScreen));
            }

            SchematicWorld schematic;
            if (KEY_BINDING_LAYER_INC.isPressed()) {
                schematic = ClientProxy.schematic;
                if (schematic != null && schematic.isRenderingLayer) {
                    schematic.renderingLayer = MathHelper.clamp_int(schematic.renderingLayer + 1, 0, schematic.getHeight() - 1);
                    RenderSchematic.INSTANCE.refresh();
                }
            }

            if (KEY_BINDING_LAYER_DEC.isPressed()) {
                schematic = ClientProxy.schematic;
                if (schematic != null && schematic.isRenderingLayer) {
                    schematic.renderingLayer = MathHelper.clamp_int(schematic.renderingLayer - 1, 0, schematic.getHeight() - 1);
                    RenderSchematic.INSTANCE.refresh();
                }
            }

            if (KEY_BINDING_LAYER_TOGGLE.isPressed()) {
                schematic = ClientProxy.schematic;
                if (schematic != null) {
                    schematic.isRenderingLayer = !schematic.isRenderingLayer;
                    RenderSchematic.INSTANCE.refresh();
                }
            }

            if (KEY_BINDING_RENDER_TOGGLE.isPressed()) {
                schematic = ClientProxy.schematic;
                if (schematic != null) {
                    schematic.isRendering = !schematic.isRendering;
                    RenderSchematic.INSTANCE.refresh();
                }
            }

            if (KEY_BINDING_PRINTER_TOGGLE.isPressed() && ClientProxy.schematic != null) {
                boolean printing = SchematicPrinter.INSTANCE.togglePrinting();
                minecraft.thePlayer.addChatComponentMessage(new ChatComponentTranslation("schematica.message.togglePrinter", new Object[]{I18n.format(printing ? "schematica.gui.on" : "schematica.gui.off", new Object[0])}));
            }

            if (KEY_BINDING_MOVE_HERE.isPressed()) {
                schematic = ClientProxy.schematic;
                if (schematic != null) {
                    ClientProxy.moveSchematicToPlayer(schematic);
                    RenderSchematic.INSTANCE.refresh();
                }
            }
            handlePickBlock();
        }
    }
}
