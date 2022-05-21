package com.gromit.gromitmod.module.fun;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.event.network.OutboundPacket;
import com.gromit.gromitmod.gui.slider.SmoothSlider;
import com.gromit.gromitmod.module.AbstractModule;
import com.gromit.gromitmod.module.crumbs.ExplosionBox;
import com.gromit.gromitmod.utils.AxisAlignedBBTime;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.gromit.gromitmod.gui.module.FunModuleGui.checkbox1;

@SideOnly(Side.CLIENT)
@Module(moduleName = "AutoTickModule")
public class AutoTick extends AbstractModule {

    private static AutoTick instance;
    private transient Minecraft minecraft = Minecraft.getMinecraft();

    public static final SmoothSlider tickslider = new SmoothSlider(100, 0, 0, 95, 2, "", 1, 4, 100);


    private int repeaterTicks;
    private int repeaterStateDelay;
    private boolean clickRepeater;

    public AutoTick() {
        instance = this;
    }

    @SubscribeEvent
    public void onPacketSend(OutboundPacket event) {

    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {

    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (!checkbox1.isState()) return;
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || minecraft.objectMouseOver == null) return;

        MovingObjectPosition mop = minecraft.objectMouseOver;

        if (event.world.getBlockState(mop.getBlockPos()).getBlock() instanceof BlockRedstoneRepeater) {

            repeaterStateDelay = event.world.getBlockState(mop.getBlockPos()).getValue(BlockRedstoneRepeater.DELAY);
            clickRepeater = true;

            if (tickslider.currentValue == 1 || tickslider.currentValue == 0) { repeaterTicks = 5; }
            else { repeaterTicks = tickslider.currentValue; }

        } else { clickRepeater = false; }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (!checkbox1.isState()) return;
        if (!clickRepeater) return;
        if (event.phase != TickEvent.Phase.END) return;
        if (repeaterStateDelay == repeaterTicks - 1) { clickRepeater = false; return; }
        if (minecraft.objectMouseOver == null) return;

        MovingObjectPosition mop = minecraft.objectMouseOver;

        if (minecraft.theWorld.getBlockState(mop.getBlockPos()).getBlock() instanceof BlockRedstoneRepeater) {
            minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mop.getBlockPos(), 1, null, 1, 1, 1));
        }
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
        minecraft = Minecraft.getMinecraft();
    }

    public static AutoTick getInstance() {
        return instance;
    }

}
