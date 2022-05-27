package com.gromit.gromitmod.module.other;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.gui.button.CheckboxButton;
import com.gromit.gromitmod.gui.slider.SmoothSlider;
import com.gromit.gromitmod.module.AbstractModule;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Module(moduleName = "AutoTickModule")
public class AutoTick extends AbstractModule {

    private static AutoTick instance;
    private transient Minecraft minecraft = Minecraft.getMinecraft();

    public final SmoothSlider tickSlider = new SmoothSlider(100, 95, 2, "", 1, 4, 100);
    public final CheckboxButton stateCheckbox = new CheckboxButton(103, 4, 4,
            button -> register(),
            button -> unregister());


    private IBlockState repeaterState;
    private BlockPos repeaterBlockPos;
    private int neededDelay;
    private boolean ticking = false;
    private boolean allowListening = true;

    public AutoTick() {
        instance = this;
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!allowListening) return;
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        IBlockState repeaterState = event.world.getBlockState(event.pos);
        if (!(repeaterState.getBlock() instanceof BlockRedstoneRepeater)) return;

        this.repeaterState = repeaterState;
        repeaterBlockPos = event.pos;
        neededDelay = tickSlider.currentValue - 1;
        if (neededDelay != repeaterState.getValue(BlockRedstoneRepeater.DELAY)) {
            if (neededDelay == 0) neededDelay = 4;
            ticking = true;
            allowListening = false;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.theWorld == null || minecraft.thePlayer == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (!ticking) {
            allowListening = true;
            return;
        }
        if (repeaterState.getValue(BlockRedstoneRepeater.DELAY) == neededDelay) {
            ticking = false;
            return;
        }
        repeaterState = minecraft.theWorld.getBlockState(repeaterBlockPos);
        if (minecraft.objectMouseOver == null) return;

        MovingObjectPosition mop = minecraft.objectMouseOver;

        if (minecraft.theWorld.getBlockState(mop.getBlockPos()).getBlock().equals(repeaterState.getBlock())) {
            minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mop.getBlockPos(), 1, minecraft.thePlayer.getCurrentEquippedItem(), 1, 1, 1));
        } else ticking = false;
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
        minecraft = Minecraft.getMinecraft();
        stateCheckbox.updateLambda(button -> register(), button -> unregister());
    }

    public static AutoTick getInstance() {
        return instance;
    }
}
