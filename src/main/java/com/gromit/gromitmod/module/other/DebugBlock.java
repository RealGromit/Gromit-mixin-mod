package com.gromit.gromitmod.module.other;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.StateDisableListener;
import com.gromit.gromitmod.gui.button.listener.StateEnableListener;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@Module(moduleName = "DebugBlockModule")
public class DebugBlock extends AbstractModule {

    @Getter private static DebugBlock instance;
    private transient RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
    private transient Minecraft minecraft = Minecraft.getMinecraft();

    public final Slider timeoutSlider = new Slider(MainGui.mainGuiPointX + 272, MainGui.mainGuiPointY + 328, false)
            .setWidth(336)
            .setHeight(8)
            .setMinMax(1, 85);

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 194, MainGui.mainGuiPointY + 186)
            .addButtonListener((StateEnableListener) button -> register())
            .addButtonListener((StateDisableListener) button -> unregister());

    private boolean coordinatesSet = false;
    private int ticks = 0;
    private float checkTime = timeoutSlider.currentValue;

    private transient List<EntityFallingBlock> sandList = new ArrayList<>();
    private transient List<EntityTNTPrimed> tntList = new ArrayList<>();
    private transient List<EntityFallingBlock> tempsand = new ArrayList<>();
    private transient List<EntityTNTPrimed> temptnt = new ArrayList<>();
    private transient List<String> messageList = new ArrayList<>();

    private boolean entityDetected = false;

    private AxisAlignedBB blockBoundingBox;
    private AxisAlignedBB radius;
    private boolean firstList = false;

    public DebugBlock() {
        instance = this;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        timeoutSlider.setCurrentProgress(84);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (!coordinatesSet) return;

        if (checkTime == timeoutSlider.currentValue && entityDetected) {
            ClientUtils.addClientMessage("");
            ClientUtils.addClientMessage("DEBUGBLOCK ENTITY DETECTED");
            ClientUtils.addClientMessage("");
            checkTime = timeoutSlider.currentValue;
        }

        if (checkTime > 0 && entityDetected) {
            ticks++;
            checkTime--;
            System.out.println(checkTime);
        }


        List<Entity> entityList = minecraft.theWorld.getEntitiesWithinAABB(Entity.class, radius);

        for (Entity entity : entityList) {

            if (tempsand.contains(entity)) {
                return;
            }
            if (temptnt.contains(entity)) {
                return;
            }

            if (entity instanceof EntityFallingBlock && entity.motionZ + entity.motionX == 0) {

                sandList.add((EntityFallingBlock) entity);
                tempsand.add((EntityFallingBlock) entity);

                entityDetected = true;


            }
            if (entity instanceof EntityTNTPrimed) {

                tntList.add((EntityTNTPrimed) entity);
                temptnt.add((EntityTNTPrimed) entity);

                entityDetected = true;

            }
        }

        String s = String.format("%02d", ticks);

        if (sandList.size() != 0) {
            messageList.add("TICK : " + s + " SAND " + sandList.size());
        }
        if (tntList.size() != 0) {
            messageList.add("TICK : " + s + " TNT " + tntList.size());
        }

        sandList.clear();
        tntList.clear();

        if (checkTime == 0 && messageList.size() != 0) {

            ClientUtils.addClientMessage("");
            ClientUtils.addClientMessage("=----DEBUGBLOCK----=");
            ClientUtils.addClientMessage("TIMEOUT : " + timeoutSlider.currentValue);
            for (String message : messageList) {
                ClientUtils.addClientMessage(message);
            }
            ClientUtils.addClientMessage("=------------------=");
            ClientUtils.addClientMessage("");

            reset();

        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.world.isRemote) return;
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        if (minecraft.thePlayer.getCurrentEquippedItem().getItem() != Items.wooden_shovel) return;
        if (entityDetected) { ClientUtils.addClientMessage("DebugBlock In Use, Try Again Later"); return; }

        Block block = event.world.getBlockState(event.pos).getBlock();
        blockBoundingBox = block.getCollisionBoundingBox(event.world, event.pos, event.world.getBlockState(event.pos));
        radius = new AxisAlignedBB(blockBoundingBox.minX - 1, blockBoundingBox.minY - 1, blockBoundingBox.minZ - 1, blockBoundingBox.maxX + 1, blockBoundingBox.maxY, blockBoundingBox.maxZ + 1);
        coordinatesSet = true;

        BlockPos blockCoordinates = event.pos;
        ClientUtils.addClientMessage("");
        ClientUtils.addClientMessage("BLOCK: " + block.getRegistryName());
        ClientUtils.addClientMessage("COORDINATES: " + blockCoordinates.getX() + ", " + blockCoordinates.getY() + ", " + blockCoordinates.getZ());
        ClientUtils.addClientMessage("");

        reset();
    }

    @SubscribeEvent
    public void onLastWorldEvent(RenderWorldLastEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (!coordinatesSet) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
        RenderUtils.drawAABBOutline(blockBoundingBox, 2, 255, 0, 0, 255);
        RenderUtils.drawAABBOutline(radius, 2, 255, 0, 0, 255);
        GlStateManager.popMatrix();
    }

    public void reset() {
        messageList.clear();
        tempsand.clear();
        temptnt.clear();
        sandList.clear();
        tntList.clear();

        ticks = 0;
        entityDetected = false;
        checkTime = timeoutSlider.currentValue;
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
        minecraft = Minecraft.getMinecraft();
        renderManager = Minecraft.getMinecraft().getRenderManager();
        sandList = new ArrayList<>();
        tntList = new ArrayList<>();
        tempsand = new ArrayList<>();
        temptnt = new ArrayList<>();
        messageList = new ArrayList<>();
        stateCheckbox
                .addButtonListener((StateEnableListener) button -> register())
                .addButtonListener((StateDisableListener) button -> unregister());
    }
}
