package com.gromit.gromitmod.module.fun;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.event.network.OutboundPacket;
import com.gromit.gromitmod.gui.slider.SmoothSlider;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.ibm.icu.impl.Differ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.gromit.gromitmod.gui.module.FunModuleGui.checkbox2;
import static com.gromit.gromitmod.gui.module.fun.DebugBlockGui.timeoutslider;

@SideOnly(Side.CLIENT)
@Module(moduleName = "DebugBlockModule")
public class DebugBlock extends AbstractModule {

    private static DebugBlock instance;
    private transient RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
    private transient Minecraft minecraft = Minecraft.getMinecraft();

    public static final SmoothSlider timeoutslider = new SmoothSlider(200, 0, 0, 84, 2, "", 1, 85, 100);


    private BlockPos blockCoordinates;
    private boolean coordinatesSet = false;
    private int ticks = 0;
    private int checkTime = timeoutslider.currentValue;

    private List<EntityFallingBlock> sandList = new ArrayList<>();
    private List<EntityTNTPrimed> tntList = new ArrayList<>();
    private List<EntityFallingBlock> tempsand = new ArrayList<>();
    private List<EntityTNTPrimed> temptnt = new ArrayList<>();
    private List<String> messageList = new ArrayList<>();

    private boolean entityDetected = false;

    private AxisAlignedBB Radius;
    private boolean firstList = false;

    public DebugBlock() {
        instance = this;
    }

    @SubscribeEvent
    public void onPacketSend(OutboundPacket event) {

    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        timeoutslider.setCurrentProgress(84);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.phase == TickEvent.Phase.END) return;
        if (!checkbox2.isState()) return;
        if (!coordinatesSet) return;

        if (checkTime == timeoutslider.currentValue && entityDetected) {
            ClientUtils.addClientMessage(minecraft, "");
            ClientUtils.addClientMessage(minecraft, "DEBUGBLOCK ENTITY DETECTED");
            ClientUtils.addClientMessage(minecraft, "");
            checkTime = timeoutslider.currentValue;
        }

        if (checkTime > 0 && entityDetected) {
            ticks++;
            checkTime--;
            System.out.println(checkTime);
        }


        List<Entity> entityList = minecraft.theWorld.getEntitiesWithinAABB(Entity.class, Radius);

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
            if (entity instanceof EntityTNTPrimed && entity.motionZ + entity.motionX == 0) {

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

            ClientUtils.addClientMessage(minecraft, "");
            ClientUtils.addClientMessage(minecraft, "=----DEBUGBLOCK----=");
            ClientUtils.addClientMessage(minecraft, "TIMEOUT : " + timeoutslider.currentValue);
            for (String message : messageList) {
                ClientUtils.addClientMessage(minecraft, message);
            }
            ClientUtils.addClientMessage(minecraft, "=------------------=");
            ClientUtils.addClientMessage(minecraft, "");

            reset();

        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (!checkbox2.isState()) return;
        if (event.world.isRemote) return;
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || minecraft.objectMouseOver == null) return;
        Item item = minecraft.thePlayer.getCurrentEquippedItem().getItem();
        if (item != Items.wooden_shovel) return;
        if (entityDetected) { ClientUtils.addClientMessage(minecraft, "DebugBlock In Use, Try Again Later"); return; }

        MovingObjectPosition mop = minecraft.objectMouseOver;
        blockCoordinates = mop.getBlockPos();
        coordinatesSet = true;

        Radius = new AxisAlignedBB(blockCoordinates.getX() - 1, blockCoordinates.getY() - 1, blockCoordinates.getZ() - 1, blockCoordinates.getX() + 2, blockCoordinates.getY() + 1, blockCoordinates.getZ() + 2);

        ClientUtils.addClientMessage(minecraft, "");
        ClientUtils.addClientMessage(minecraft, "BLOCK : " + event.world.getBlockState(mop.getBlockPos()).getBlock().getRegistryName());
        ClientUtils.addClientMessage(minecraft, "COORDINATES : " + blockCoordinates.getX() + ", " + blockCoordinates.getY() + ", " + blockCoordinates.getZ());
        ClientUtils.addClientMessage(minecraft, "");

        reset();
    }

    @SubscribeEvent
    public void onLastWorldEvent(RenderWorldLastEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (!coordinatesSet) return;
        if (!checkbox2.isState()) return;

        AxisAlignedBB Block = new AxisAlignedBB(blockCoordinates.getX(), blockCoordinates.getY(), blockCoordinates.getZ(), blockCoordinates.getX() + 1, blockCoordinates.getY() + 1, blockCoordinates.getZ() + 1);

        GlStateManager.pushMatrix();
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);

        RenderUtils.drawAABBOutline(Block, 2, 255, 0, 0, 255);
        RenderUtils.drawAABBOutline(Radius, 2, 255, 0, 0, 255);
        GlStateManager.popMatrix();
    }

    public void reset() {
        messageList.clear();
        tempsand.clear();
        temptnt.clear();
        sandList.clear();
        tntList.clear();

        Radius = new AxisAlignedBB(blockCoordinates.getX() - 1, blockCoordinates.getY() - 1, blockCoordinates.getZ() - 1, blockCoordinates.getX() + 2, blockCoordinates.getY() + 1, blockCoordinates.getZ() + 2);

        ticks = 0;
        entityDetected = false;
        checkTime = timeoutslider.currentValue;
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
        minecraft = Minecraft.getMinecraft();
        renderManager = Minecraft.getMinecraft().getRenderManager();
    }

    public static DebugBlock getInstance() {
        return instance;
    }

}
