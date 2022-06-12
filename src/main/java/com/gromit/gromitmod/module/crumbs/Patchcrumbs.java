package com.gromit.gromitmod.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.StateDisableListener;
import com.gromit.gromitmod.gui.button.listener.StateEnableListener;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.listener.ChunkMapper;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.MathUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.List;

@Module(moduleName = "PatchcrumbsModule")
public class Patchcrumbs extends AbstractModule {

    private static Patchcrumbs instance;
    private static final GromitMod gromitMod = GromitMod.getInstance();
    private transient Minecraft minecraft = Minecraft.getMinecraft();
    private transient RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
    private transient ChunkMapper chunkMapper = ChunkMapper.getInstance();

    public final ColorButton boxColorButton = new ColorButton(MainGui.mainGuiPointX + 130, MainGui.mainGuiPointY + 50.3f, MainGui.mainGuiPointX + MainGui.guiWidth + 5, MainGui.mainGuiPointY, 40, 40)
            .setWidth(4)
            .setHeight(4);

    public final ColorButton outlineColorButton = new ColorButton(MainGui.mainGuiPointX + 130, MainGui.mainGuiPointY + 57.3f, MainGui.mainGuiPointX + MainGui.guiWidth + 5, MainGui.mainGuiPointY + 51, 40, 40)
            .setWidth(4)
            .setHeight(4);

    public final ColorButton lineColorButton = new ColorButton(MainGui.mainGuiPointX + 130, MainGui.mainGuiPointY + 64.3f, MainGui.mainGuiPointX + MainGui.guiWidth + 5, MainGui.mainGuiPointY + 102, 40, 40)
            .setWidth(4)
            .setHeight(4);

    public final Slider timeoutSlider = new Slider(MainGui.mainGuiPointX + 68, MainGui.mainGuiPointY + 82, false)
            .setWidth(95)
            .setHeight(2)
            .setMinMax(1, 20);

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 48.5f, MainGui.mainGuiPointY + 46.8f)
            .addButtonListener((StateEnableListener) button -> register())
            .addButtonListener((StateDisableListener) button -> unregister());

    // Set for all detections that are possibly incoming shots
    //private transient HashSet<AxisAlignedBBTime> detectionSet = new HashSet<>();
    private transient Object2ByteOpenHashMap<AxisAlignedBBTime> detectionSet = new Object2ByteOpenHashMap<>();
    // Set for incoming shots that need to be drawn
    //private transient HashSet<AxisAlignedBBTime> drawSet = new HashSet<>();
    private transient Object2ByteOpenHashMap<AxisAlignedBBTime> drawSet = new Object2ByteOpenHashMap<>();
    private transient HashSet<Integer> entityIds = new HashSet<>();
    private transient Long2LongOpenHashMap blockXZ = new Long2LongOpenHashMap();

    private transient double oldX;
    private transient double oldY;
    private transient double oldZ;

    private transient long tickCounter;

    public Patchcrumbs() {
        instance = this;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.thePlayer == null || minecraft.theWorld == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        tickCounter++;

        if (tickCounter % 600 == 0) {
            entityIds.clear();
            tickCounter = 0;
        }

        List<Entity> entityList = minecraft.theWorld.getLoadedEntityList();
        long time = System.currentTimeMillis();

        for (Entity entity : entityList) {
            if (!(entity instanceof EntityFallingBlock)) continue;
            if (entity.motionX + entity.motionZ == 0) {
                if (chunkMapper.getCannonChunks().contains(MathUtils.int2Long(entity.chunkCoordX, entity.chunkCoordZ))) continue;
                if (oldX == entity.posX && oldY == entity.posY && oldZ == entity.posZ) continue;
                oldX = entity.posX;
                oldY = entity.posY;
                oldZ = entity.posZ;
                if (entityIds.contains(entity.getEntityId())) continue;
                entityIds.add(entity.getEntityId());
                if (blockXZ.containsKey(MathUtils.int2Long((int) entity.posX, (int) entity.posZ))) continue;
                if (minecraft.theWorld.getEntitiesWithinAABB(EntityFallingBlock.class, entity.getEntityBoundingBox()).size() < 3) continue;
                byte direction = isCollidedWithBlock(entity.worldObj, entity.posX, entity.posY, entity.posZ);
                if (direction == 0) continue;
                AxisAlignedBBTime box = new AxisAlignedBBTime(entity.getEntityBoundingBox(), time);
                box.expandToBlock();
                box.roundY();
                detectionSet.put(box, direction);
            }
        }
        ObjectIterator<Object2ByteMap.Entry<AxisAlignedBBTime>> iterator = detectionSet.object2ByteEntrySet().fastIterator();
        while (iterator.hasNext()) {
            Object2ByteMap.Entry<AxisAlignedBBTime> pair = iterator.next();
            AxisAlignedBBTime box = pair.getKey();
            if (time - box.getTime() > 4000) {
                iterator.remove();
                continue;
            }
            blockXZ.put(MathUtils.int2Long((int) box.minX, (int) box.minZ), time);
            Block block = minecraft.theWorld.getBlockState(new BlockPos(box.minX + 0.5, box.minY - 0.5, box.minZ + 0.5)).getBlock();
            Block block1 = minecraft.theWorld.getBlockState(new BlockPos(box.minX + 0.5, box.minY - 1.5, box.minZ + 0.5)).getBlock();
            if (block instanceof BlockFalling || block1 instanceof BlockFalling) {
                iterator.remove();
                box.setTime(time);
                drawSet.put(box, pair.getByteValue());
            }
        }
        ObjectIterator<Long2LongMap.Entry> iterator1 = blockXZ.long2LongEntrySet().fastIterator();
        while (iterator1.hasNext()) {
            Long2LongMap.Entry pair = iterator1.next();
            if (time - pair.getLongValue() > 2000) iterator1.remove();
        }
        ObjectIterator<Object2ByteMap.Entry<AxisAlignedBBTime>> iterator2 = drawSet.object2ByteEntrySet().fastIterator();
        while (iterator2.hasNext()) {
            Object2ByteMap.Entry<AxisAlignedBBTime> pair = iterator2.next();
            if (time - pair.getKey().getTime() > 2900) iterator2.remove();
        }
    }

    @SubscribeEvent
    public void onRenderLastWorld(RenderWorldLastEvent event) {
        // Currently, using detectionSet due to only having 1 detection method for now
        if (drawSet.isEmpty()) return;
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);

        ObjectIterator<Object2ByteMap.Entry<AxisAlignedBBTime>> iterator = drawSet.object2ByteEntrySet().fastIterator();
        while (iterator.hasNext()) {
            Object2ByteMap.Entry<AxisAlignedBBTime> pair = iterator.next();
            AxisAlignedBBTime box = pair.getKey();
            RenderUtils.drawAABB(box, boxColorButton.getRed(), boxColorButton.getGreen(), boxColorButton.getBlue(), boxColorButton.getAlpha());
            RenderUtils.drawAABBOutline(box, 2, outlineColorButton.getRed(), outlineColorButton.getGreen(), outlineColorButton.getBlue(), outlineColorButton.getAlpha());
            RenderUtils.drawPatchcrumbsLine(box, 2, lineColorButton.getRed(), lineColorButton.getGreen(), lineColorButton.getBlue(), lineColorButton.getAlpha(), pair.getByteValue());
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private byte isCollidedWithBlock(World world, double x, double y, double z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockPos north = blockPos.north();
        BlockPos south = blockPos.south();
        BlockPos west = blockPos.west();
        BlockPos east = blockPos.east();
        IBlockState northState = world.getBlockState(north);
        IBlockState southSate = world.getBlockState(south);
        IBlockState westState = world.getBlockState(west);
        IBlockState eastState = world.getBlockState(east);
        if (northState.getBlock().getCollisionBoundingBox(world, north, northState) != null
                || southSate.getBlock().getCollisionBoundingBox(world, south, southSate) != null) return 1;
        if (westState.getBlock().getCollisionBoundingBox(world, west, westState) != null
                || eastState.getBlock().getCollisionBoundingBox(world, east, eastState) != null) return 2;
        return 0;
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
        chunkMapper = ChunkMapper.getInstance();
        stateCheckbox
                .addButtonListener((StateEnableListener) button -> register())
                .addButtonListener((StateDisableListener) button -> unregister());
    }

    public static Patchcrumbs getInstance() {
        return instance;
    }
}