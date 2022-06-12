package com.gromit.gromitmod.gui.button;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.gromit.gromitmod.gui.button.listener.*;
import com.gromit.gromitmod.gui.schematica.SchematicLoadGui;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import com.gromit.gromitmod.utils.fontrenderer.TTFFontRenderer;
import com.gromit.gromitmod.utils.schematic.PlayerData;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class SchematicButton extends AbstractButton<SchematicButton> {

    private final SchematicLoadGui schematicLoadGui = SchematicLoadGui.getInstance();
    @Setter @Getter private List<PlayerData> playerDataList;
    @Setter @Getter private File file;
    @Getter private final boolean directory;
    private int currentIndex;

    @Getter protected String buttonText;
    @Getter protected int color = Color.WHITE.getRGB();
    @Getter protected TTFFontRenderer fontRenderer;

    @Getter private final TextButton recordButton = new TextButton(FontManager.getTitleSize(), 345 - FontManager.getNormalSize().getWidth("Record") / 2, 60);
    @Getter private final TextButton playButton = new TextButton(FontManager.getTitleSize(), 390 - FontManager.getNormalSize().getWidth("Play") / 2, 60);

    public SchematicButton(TTFFontRenderer fontRenderer, int x, int y, File file, boolean directory) {
        super(x, y);
        this.fontRenderer = fontRenderer;
        if (fontRenderer.equals(FontManager.getNormalSize())) height = 4;
        else if (fontRenderer.equals(FontManager.getTitleSize())) height = 6;
        this.file = file;
        this.directory = directory;
        recordButton
                .setButtonText("Record")
                .addButtonListener((StateEnableListener) button -> {
                    playButton.setState(false);
                    playerDataList.clear();
                    MinecraftForge.EVENT_BUS.register(this);
                })
                .addButtonListener((StateDisableListener) button -> MinecraftForge.EVENT_BUS.unregister(this));
        playButton
                .setButtonText("Play")
                .addButtonListener((StateEnableListener) button -> {
                    recordButton.setState(false);
                    MinecraftForge.EVENT_BUS.register(this);
                })
                .addButtonListener((StateDisableListener) button -> MinecraftForge.EVENT_BUS.unregister(this));
        addButtonListener((ClickDisableListener) button -> {
            Schematica.proxy.loadSchematic(minecraft.thePlayer, file.getParentFile(), file.getName());
            if (ClientProxy.schematic != null) ClientProxy.moveSchematicToPlayer(ClientProxy.schematic);
            minecraft.displayGuiScreen(null);
        });
        addButtonListener((EndHoverListener) button -> ((SchematicButton) button).setColor(Color.WHITE.getRGB()));
        if (directory) {
            addButtonListener((ClickListener) button -> {
                schematicLoadGui.changeDirectory(((SchematicButton) button).getFile());
                button.setState(false);
            });
        }
    }

    @Override
    public void drawButton(float mouseX, float mouseY) {
        if (!enabled) return;
        super.drawButton(mouseX, mouseY);

        if (state) {
            color = ColorUtils.getRGB();
            drawAutoPrintMenu(mouseX, mouseY);
        }
        else if (hovering) color = ColorUtils.getRGB();
        fontRenderer.drawString(buttonText, x, y, color);
    }

    private void drawAutoPrintMenu(float mouseX, float mouseY) {
        FontManager.getTitleSize().drawString("Packets recorded: " + playerDataList.size(), 367 - FontManager.getTitleSize().getWidth("Packets recorded: " + playerDataList.size()) / 2, 35, Color.WHITE.getRGB());
        recordButton.drawButton(mouseX, mouseY);
        playButton.drawButton(mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(float mouseButton, float mouseX, float mouseY) {
        super.mousePressed(mouseButton, mouseX, mouseY);

        if (state && hovering) {
            for (List<SchematicButton> schematicButtons : schematicLoadGui.getDirectoryMap().values()) {
                for (SchematicButton schematicButton : schematicButtons) {
                    if (!schematicButton.equals(this)) schematicButton.setState(false);
                }
            }
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        if (playButton.isState() && minecraft.currentScreen == null) {
            if (currentIndex + 1 > playerDataList.size()) {
                playButton.setState(false);
                currentIndex = 0;
                return;
            }
            PlayerData playerData = playerDataList.get(currentIndex);
            minecraft.thePlayer.setPosition(playerData.posX, playerData.posY, playerData.posZ);
            minecraft.thePlayer.rotationYaw = playerData.yaw;
            minecraft.thePlayer.rotationPitch = playerData.pitch;
            currentIndex++;
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!event.entity.equals(minecraft.thePlayer)) return;

        if (recordButton.isState() && minecraft.currentScreen == null) {
            Entity player = event.entityLiving;
            playerDataList.add(new PlayerData(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch));
        }
    }

    public SchematicButton setButtonText(String buttonText) {
        this.buttonText = buttonText;
        width = fontRenderer.getWidth(buttonText);
        return this;
    }

    public SchematicButton setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public void setState(boolean state) {
        super.setState(state);

        if (!state) color = Color.WHITE.getRGB();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SchematicButton)) return false;
        return file.getAbsolutePath().equals(((SchematicButton) obj).file.getAbsolutePath());
    }

    @Override
    public int hashCode() {return Objects.hashCode(file.getAbsolutePath());}
}