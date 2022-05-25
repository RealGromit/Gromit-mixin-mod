package com.gromit.gromitmod.module.crumbs;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.listener.ChunkMapper;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
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
    private transient Minecraft minecraft = Minecraft.getMinecraft();
    private transient RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
    private transient ChunkMapper chunkMapper = ChunkMapper.getInstance();

    public final ColorButton boxColorButton = new ColorButton(14, 4, 4, 0, 0, 40, 40);
    public final ColorButton outlineColorButton = new ColorButton(15, 4, 4, 0, 0, 40, 40);
    public final ColorButton lineColorButton = new ColorButton(16, 4, 4, 0, 0, 40, 40);
    public final Slider timeoutSlider = new Slider(17, 95, 2, 1, 20, 100);
    public final CheckboxButton stateCheckbox = new CheckboxButton(18, 4, 4,
            button -> register(),
            button -> unregister());

    // Set for all detections that are possibly incoming shots
    private transient HashSet<AxisAlignedBBTime> detectionSet = new HashSet<>();
    // Set for incoming shots that need to be drawn
    private transient HashSet<AxisAlignedBBTime> drawSet = new HashSet<>();

    private transient HashSet<Integer> entityIds = new HashSet<>();

    private transient double oldX;
    private transient double oldY;
    private transient double oldZ;

    private long tickCounter;

    public Patchcrumbs() {
        instance = this;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.thePlayer == null || minecraft.theWorld == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        tickCounter++;

        if (tickCounter % 600 == 0) entityIds.clear();

        List<Entity> entityList = minecraft.theWorld.getLoadedEntityList();
        long time = System.currentTimeMillis();

        for (Entity entity : entityList) {
            if (!(entity instanceof EntityFallingBlock) && !(entity instanceof EntityTNTPrimed)) continue;
            if (entity.motionX + entity.motionZ == 0) {
                if (oldX == entity.posX && oldY == entity.posY && oldZ == entity.posZ) continue;
                oldX = entity.posX;
                oldY = entity.posY;
                oldZ = entity.posZ;
                if (chunkMapper.getCannonChunks().contains((((long) entity.chunkCoordX) << 32) | (entity.chunkCoordZ & 0xffffffffL))) continue;
                if (entityIds.contains(entity.getEntityId())) continue;
                entityIds.add(entity.getEntityId());
                if (minecraft.theWorld.getEntitiesWithinAABB(EntityFallingBlock.class, entity.getEntityBoundingBox()).size() < 3
                        || minecraft.theWorld.getEntitiesWithinAABB(EntityTNTPrimed.class, entity.getEntityBoundingBox()).size() < 2) continue;
                if (!isCollidedWithBlock(entity.worldObj, entity.posX, entity.posY, entity.posZ)) continue;
                AxisAlignedBBTime axisAlignedBBTime = new AxisAlignedBBTime(entity.getEntityBoundingBox(), time);
                axisAlignedBBTime.expandToBlock();
                axisAlignedBBTime.roundY();
                detectionSet.add(axisAlignedBBTime);
            }
        }
        detectionSet.removeIf(box -> time - box.getTime() > 4000);
    }

    @SubscribeEvent
    public void onRenderLastWorld(RenderWorldLastEvent event) {
        // Currently, using detectionSet due to only having 1 detection method for now
        if (detectionSet.isEmpty()) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
        for (AxisAlignedBBTime box : detectionSet) {
            RenderUtils.drawAABB(box, boxColorButton.getRed(), boxColorButton.getGreen(), boxColorButton.getBlue(), boxColorButton.getAlpha());
            RenderUtils.drawAABBOutline(box, 2, outlineColorButton.getRed(), outlineColorButton.getGreen(), outlineColorButton.getBlue(), outlineColorButton.getAlpha());
        }
        GlStateManager.popMatrix();
    }

    private boolean isCollidedWithBlock(World world, double x, double y, double z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockPos north = blockPos.north();
        BlockPos south = blockPos.south();
        BlockPos west = blockPos.west();
        BlockPos east = blockPos.east();
        IBlockState northState = world.getBlockState(north);
        IBlockState southSate = world.getBlockState(south);
        IBlockState westState = world.getBlockState(west);
        IBlockState eastState = world.getBlockState(east);
        return northState.getBlock().getCollisionBoundingBox(world, north, northState) != null
                || southSate.getBlock().getCollisionBoundingBox(world, south, southSate) != null
                || westState.getBlock().getCollisionBoundingBox(world, west, westState) != null
                || eastState.getBlock().getCollisionBoundingBox(world, east, eastState) != null;
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
        chunkMapper = ChunkMapper.getInstance();
        ColorUtils.colorButtons.add(boxColorButton);
        ColorUtils.colorButtons.add(outlineColorButton);
        ColorUtils.colorButtons.add(lineColorButton);
    }

    public static Patchcrumbs getInstance() {
        return instance;
    }
}