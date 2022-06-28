package com.gromit.gromitmod.module.fps;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.event.network.InboundPacketEvent;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.StateDisableListener;
import com.gromit.gromitmod.gui.button.listener.StateEnableListener;
import com.gromit.gromitmod.module.AbstractModule;
import lombok.Getter;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module(moduleName = "FallingBlockModule")
public class FallingBlock extends AbstractModule {

    @Getter private static FallingBlock instance;

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 194, MainGui.mainGuiPointY + 186)
            .addButtonListener((StateEnableListener) button -> register())
            .addButtonListener((StateDisableListener) button -> unregister());

    public final ToggleButton fallingBlockXRay = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 201);
    public final ToggleButton fallingBlockDropShadow = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 229);
    public final ToggleButton fallingBlockDisable = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 257);

    public FallingBlock() {
        instance = this;
    }

    @SubscribeEvent
    public void onEntityPacket(InboundPacketEvent event) {
        if (!(event.getPacket() instanceof S0EPacketSpawnObject)) return;

        if (fallingBlockDisable.isState() && ((S0EPacketSpawnObject) event.getPacket()).getType() == 70) event.setCanceled(true);
    }

    @Override
    public void updateAfterDeserialization() {
        stateCheckbox
                .addButtonListener((StateEnableListener) button -> register())
                .addButtonListener((StateDisableListener) button -> unregister());
    }
}
