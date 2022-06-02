package com.gromit.gromitmod.gui.button;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.gromit.gromitmod.gui.schematica.SchematicLoadGui;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import com.gromit.gromitmod.utils.schematic.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class SchematicButton extends AbstractBaseButton {

    private final SchematicLoadGui schematicLoadGui = SchematicLoadGui.getInstance();
    public List<PlayerData> playerDataList;
    private File file;
    private final boolean directory;
    private int currentIndex;

    private final TextButton recordButton = new TextButton(gromitMod.getNewButtonId(), 4, "Record",
            button -> {},
            button -> {});

    private final TextButton playButton = new TextButton(gromitMod.getNewButtonId(), 4, "Play",
            button -> {},
            button -> {});

    public SchematicButton(int buttonId, int height, String displayString, File file, boolean directory) {
        super(buttonId, (int) (FontUtil.title.getStringWidth(displayString) / 2), height, displayString);
        this.file = file;
        this.directory = directory;
        recordButton.updateLambda(button -> {
            MinecraftForge.EVENT_BUS.register(this);
            playButton.setState(false);
        },
        button -> MinecraftForge.EVENT_BUS.unregister(this));

        playButton.updateLambda(button -> {
            MinecraftForge.EVENT_BUS.register(this);
            recordButton.setState(false);
        },
        button -> MinecraftForge.EVENT_BUS.unregister(this));
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        mouseX /= guiScale;
        mouseY /= guiScale;
        hovered = mouseX >= xPosition && mouseY >= yPosition + schematicLoadGui.scroll && mouseX < xPosition + width && mouseY < yPosition + height + schematicLoadGui.scroll;
        if (state) {
            FontUtil.title.drawString(displayString, xPosition + 0.5, yPosition + 2 + schematicLoadGui.scroll, ColorUtils.getRGB());
            drawAutoPrintMenu(minecraft, mouseX, mouseY);
        }
        else if (hovered) {
            FontUtil.title.drawString(displayString, xPosition + 0.5, yPosition + 2 + schematicLoadGui.scroll, ColorUtils.getRGB());
        }
        else FontUtil.title.drawString(displayString, xPosition + 0.5, yPosition + 2 + schematicLoadGui.scroll, Color.WHITE.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (hovered) {
            state = !state;
            if (!state) {
                Schematica.proxy.loadSchematic(minecraft.thePlayer, file.getParentFile(), file.getName());
                if (ClientProxy.schematic != null) ClientProxy.moveSchematicToPlayer(ClientProxy.schematic);
                minecraft.displayGuiScreen(null);
            }
            if (directory && state) {
                schematicLoadGui.changeDirectory(file);
                state = false;
            } return true;
        } return false;
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
    public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!event.entityLiving.equals(minecraft.thePlayer)) return;

        if (recordButton.isState() && minecraft.currentScreen == null) {
            Entity player = event.entityLiving;
            playerDataList.add(new PlayerData(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch));
        }
    }

    private void drawAutoPrintMenu(Minecraft minecraft, int mouseX, int mouseY) {
        FontUtil.normal.drawString("Packets recorded: " + playerDataList.size(), 367 - FontUtil.normal.getStringWidth("Packets recorded: " + playerDataList.size()) / 4, 35, Color.WHITE.getRGB());
        mouseX *= guiScale;
        mouseY *= guiScale;
        recordButton.drawButton(minecraft, mouseX, mouseY);
        playButton.drawButton(minecraft, mouseX, mouseY);
    }

    public void updateButtonList(List<GuiButton> buttonList) {
        buttonList.add(recordButton);
        buttonList.add(playButton);
        recordButton.updateButton((int) (345 - FontUtil.normal.getStringWidth("Record") / 4), 60, guiScale);
        playButton.updateButton((int) (390 - FontUtil.normal.getStringWidth("Play") / 4), 60, guiScale);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SchematicButton)) return false;
        return file.getAbsolutePath().equals(((SchematicButton) obj).file.getAbsolutePath());
    }

    @Override
    public int hashCode() {return Objects.hashCode(file.getAbsolutePath());}

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isDirectory() {
        return directory;
    }

    public List<PlayerData> getPlayerData() {
        return playerDataList;
    }

    public void setPlayerData(List<PlayerData> playerDataList) {
        this.playerDataList = playerDataList;
    }
}