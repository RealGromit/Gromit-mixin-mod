package com.gromit.gromitmod.module.fps;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.event.network.InboundPacketEvent;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.StateDisableListener;
import com.gromit.gromitmod.gui.button.listener.StateEnableListener;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.AbstractModule;
import lombok.Getter;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module(moduleName = "TntModule")
public class Tnt extends AbstractModule {

    @Getter private static Tnt instance;

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 194, MainGui.mainGuiPointY + 158)
            .addButtonListener((StateEnableListener) button -> register())
            .addButtonListener((StateDisableListener) button -> unregister());

    public final ToggleButton tntFlash = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 201);
    public final ToggleButton tntXRay = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 229);
    public final ToggleButton tntSwell = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 257);
    public final ToggleButton tntFuseLabel = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 285);
    public final ToggleButton tntDropShadow = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 313);
    public final ToggleButton tntSmokeParticle = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 341);
    public final ToggleButton tntOptimize = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 369);
    public final ToggleButton tntDisable = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 397);
    public final Slider tntLimiter = new Slider(MainGui.mainGuiPointX + 272, MainGui.mainGuiPointY + 452, false)
            .setWidth(396)
            .setHeight(8)
            .setMinMax(2, 200);
    public final ToggleButton tntLimiterState = new ToggleButton(MainGui.mainGuiPointX + 518, MainGui.mainGuiPointY + 425);


    public Tnt() {
        instance = this;
    }

    @SubscribeEvent
    public void onEntityPacket(InboundPacketEvent event) {
        if (!tntDisable.isState()) return;
        if (!(event.getPacket() instanceof S0EPacketSpawnObject)) return;

        if (((S0EPacketSpawnObject) event.getPacket()).getType() == 50) event.setCanceled(true);
    }

    @Override
    public void updateAfterDeserialization() {
        stateCheckbox
                .addButtonListener((StateEnableListener) button -> register())
                .addButtonListener((StateDisableListener) button -> unregister());
    }
}