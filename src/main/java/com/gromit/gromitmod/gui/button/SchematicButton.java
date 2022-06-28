package com.gromit.gromitmod.gui.button;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.gromit.gromitmod.event.network.OutboundPacketEvent;
import com.gromit.gromitmod.gui.button.listener.*;
import com.gromit.gromitmod.gui.schematica.SchematicLoadGui;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.TTFFontRenderer;
import com.gromit.gromitmod.utils.primitivewrapper.GromitPosDouble;
import com.gromit.gromitmod.utils.schematic.PlayerData;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.*;

public class SchematicButton extends AbstractButton<SchematicButton> {

    private final SchematicLoadGui schematicLoadGui = SchematicLoadGui.getInstance();
    public static SchematicButton currentSchematicButton;
    @Setter @Getter private TreeMap<Integer, PlayerData> playerDataMap;
    @Setter @Getter private File file;
    @Getter private final boolean directory;
    private int currentIndex;
    private SchematicWorld schematic;
    private List<PlayerData> playerDataList;
    private int tickCounter;
    private GromitPosDouble recordPos;
    private GromitPosDouble playPos;
    private float oldYaw;
    private float oldPitch;
    private double oldX;
    private double oldY;
    private double oldZ;
    private boolean firstPacket = true;

    @Getter protected String buttonText;
    protected int color = Color.WHITE.getRGB();
    protected TTFFontRenderer fontRenderer;

    @Getter private final TextButton recordButton = new TextButton(title, 1335 - title.getWidth("Record") / 2, 280);
    @Getter private final TextButton clearButton = new TextButton(title, 1470 - title.getWidth("Clear") / 2, 280)
            .setButtonText("Clear")
            .addButtonListener((ClickListener) button -> playerDataMap.clear())
            .addButtonListener((StateEnableListener) button -> button.setState(false));

    @Getter private final TextButton playButton = new TextButton(title, 1605 - title.getWidth("Play") / 2, 280);
    @Getter private final ToggleButton autoPlay = new ToggleButton(1550, 202);

    public SchematicButton(TTFFontRenderer fontRenderer, int x, int y, File file, boolean directory) {
        super(x, y);
        this.fontRenderer = fontRenderer;
        if (fontRenderer.equals(normal)) height = 16;
        else if (fontRenderer.equals(title)) height = 24;
        this.file = file;
        this.directory = directory;
        recordButton
                .setButtonText("Record")
                .addButtonListener((StateEnableListener) button -> {
                    playButton.setState(false);
                    playerDataMap.clear();
                    schematic = ClientProxy.schematic;
                    tickCounter = 0;
                    firstPacket = true;
                    recordPos = new GromitPosDouble(minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ);
                    MinecraftForge.EVENT_BUS.register(this);
                })
                .addButtonListener((StateDisableListener) button -> MinecraftForge.EVENT_BUS.unregister(this));
        playButton
                .setButtonText("Play")
                .addButtonListener((StateEnableListener) button -> {
                    recordButton.setState(false);
                    currentIndex = 0;
                    playerDataList = new ArrayList<>(playerDataMap.values());
                    playPos = new GromitPosDouble(minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ);
                    MinecraftForge.EVENT_BUS.register(this);
                })
                .addButtonListener((StateDisableListener) button -> MinecraftForge.EVENT_BUS.unregister(this));
        addButtonListener((ClickDisableListener) button -> {
            Schematica.proxy.loadSchematic(minecraft.thePlayer, file.getParentFile(), file.getName());
            if (ClientProxy.schematic != null) ClientProxy.moveSchematicToPlayer(ClientProxy.schematic);
            minecraft.displayGuiScreen(null);
            currentSchematicButton = this;
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
    public void drawButton(int mouseX, int mouseY) {
        if (!enabled) return;
        super.drawButton(mouseX, mouseY);

        if (state) {
            color = ColorUtils.getRGB();
            drawAutoPrintMenu(mouseX, mouseY);
        }
        else if (hovering) color = ColorUtils.getRGB();
        fontRenderer.drawString(buttonText, x, y, color);
    }

    private void drawAutoPrintMenu(int mouseX, int mouseY) {
        title.drawString("Schematic replay menu", 1330, 140, Color.WHITE.getRGB());
        normal.drawString("Play on schematic load", 1280, 200, Color.WHITE.getRGB());
        autoPlay.drawButton(mouseX, mouseY);
        recordButton.drawButton(mouseX, mouseY);
        clearButton.drawButton(mouseX, mouseY);
        playButton.drawButton(mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(int mouseButton, int mouseX, int mouseY) {
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
    public void onPlayerPacketSend(OutboundPacketEvent event) {
        if (!recordButton.isState() || minecraft.currentScreen != null) return;
        if (firstPacket) {
            oldYaw = minecraft.thePlayer.rotationYaw;
            oldPitch = minecraft.thePlayer.rotationPitch;
            oldX = -(recordPos.getX() - minecraft.thePlayer.posX);
            oldY = -(recordPos.getY() - minecraft.thePlayer.posY);
            oldZ = -(recordPos.getZ() - minecraft.thePlayer.posZ);
            firstPacket = false;
        }
        if (event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            C03PacketPlayer.C06PacketPlayerPosLook packet = (C03PacketPlayer.C06PacketPlayerPosLook) event.getPacket();
            PlayerData playerData = playerDataMap.computeIfAbsent(tickCounter, object -> new PlayerData());

            oldX = playerData.posX = -(recordPos.getX() - packet.getPositionX());
            oldY = playerData.posY = -(recordPos.getY() - packet.getPositionY());
            oldZ = playerData.posZ = -(recordPos.getZ() - packet.getPositionZ());
            oldYaw = playerData.yaw = packet.getYaw();
            oldPitch = playerData.pitch = packet.getPitch();
            playerData.jumping = minecraft.thePlayer.movementInput.jump;
            playerData.sneaking = minecraft.thePlayer.movementInput.sneak;
        } else if (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) {
            C03PacketPlayer.C04PacketPlayerPosition packet = (C03PacketPlayer.C04PacketPlayerPosition) event.getPacket();
            PlayerData playerData = playerDataMap.computeIfAbsent(tickCounter, object -> new PlayerData());

            oldX = playerData.posX = -(recordPos.getX() - packet.getPositionX());
            oldY = playerData.posY = -(recordPos.getY() - packet.getPositionY());
            oldZ = playerData.posZ = -(recordPos.getZ() - packet.getPositionZ());
            playerData.yaw = oldYaw;
            playerData.pitch = oldPitch;
            playerData.jumping = minecraft.thePlayer.movementInput.jump;
            playerData.sneaking = minecraft.thePlayer.movementInput.sneak;
        } else if (event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) {
            C03PacketPlayer.C05PacketPlayerLook packet = (C03PacketPlayer.C05PacketPlayerLook) event.getPacket();
            PlayerData playerData = playerDataMap.computeIfAbsent(tickCounter, object -> new PlayerData());

            playerData.posX = oldX;
            playerData.posY = oldY;
            playerData.posZ = oldZ;
            oldYaw = playerData.yaw = packet.getYaw();
            oldPitch = playerData.pitch = packet.getPitch();
            playerData.jumping = minecraft.thePlayer.movementInput.jump;
            playerData.sneaking = minecraft.thePlayer.movementInput.sneak;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        tickCounter++;
        if (playButton.isState()) {
            if (currentIndex + 1 > playerDataList.size()) {
                playButton.setState(false);
                currentIndex = 0;
                return;
            }
            PlayerData playerData = playerDataList.get(currentIndex);
            minecraft.thePlayer.setPosition(playPos.getX() + playerData.posX, playPos.getY() + playerData.posY, playPos.getZ() + playerData.posZ);
            minecraft.thePlayer.rotationYaw = playerData.yaw;
            minecraft.thePlayer.rotationPitch = playerData.pitch;
            if (playerData.jumping) minecraft.thePlayer.jump();
            minecraft.thePlayer.setJumping(playerData.jumping);
            minecraft.thePlayer.movementInput.sneak = playerData.sneaking;
            minecraft.thePlayer.setSneaking(playerData.sneaking);
            currentIndex++;
        }
    }

    public void rotate(EnumFacing enumFacing) {
        Set<Integer> keySet = playerDataMap.keySet();
    }

    public void flip(EnumFacing enumFacing) {
        if (playerDataMap.isEmpty()) return;

        boolean axisX = enumFacing.getAxis().getName().equalsIgnoreCase("x");
        boolean axisZ = enumFacing.getAxis().getName().equalsIgnoreCase("z");
        Set<Integer> keySet = playerDataMap.keySet();
        if (axisX) {
            for (int key : keySet) {
                PlayerData playerData = playerDataMap.get(key);

                if (playerData.yaw > 0) playerData.yaw -= 180;
                else playerData.yaw += 180;

                playerData.posX = -playerData.posX;
                playerDataMap.put(key, playerData);
            }
        } else if (axisZ) {
            for (int key : keySet) {
                PlayerData playerData = playerDataMap.get(key);

                if (playerData.yaw > 0) playerData.yaw -= 180;
                else playerData.yaw += 180;

                playerData.posZ = -playerData.posZ;
                playerDataMap.put(key, playerData);
            }
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