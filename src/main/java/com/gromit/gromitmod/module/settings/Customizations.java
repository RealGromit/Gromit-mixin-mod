package com.gromit.gromitmod.module.settings;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.gui.MainGui;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.button.listener.StateDisableListener;
import com.gromit.gromitmod.gui.button.listener.StateEnableListener;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.AbstractModule;
import lombok.Getter;

@Module(moduleName = "CustomizationsModule")
public class Customizations extends AbstractModule {

    @Getter private static Customizations instance;

    public final ToggleButton stateCheckbox = new ToggleButton(MainGui.mainGuiPointX + 194, MainGui.mainGuiPointY + 158)
            .addButtonListener((StateEnableListener) button -> register())
            .addButtonListener((StateDisableListener) button -> unregister());

    public final ToggleButton circleRgb = new ToggleButton(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 201);
    public final Slider circleRadius = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 233, false)
            .setWidth(160)
            .setHeight(8)
            .setMinMax(40, 60);
    public final Slider circleLineWidth = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 261, false)
            .setWidth(180)
            .setHeight(8)
            .setMinMax(1, 7);
    public final Slider circleFade = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 289, false)
            .setWidth(180)
            .setHeight(8)
            .setMinMax(1, 7);
    public final Slider circleSpeed = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 317, false)
            .setWidth(180)
            .setHeight(8)
            .setMinMax(1, 10);
    public final ColorButton circleColor = new ColorButton(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 340, MainGui.mainGuiPointX + MainGui.guiWidth + 25, MainGui.mainGuiPointY, 240, 240)
            .setWidth(16)
            .setHeight(16);

    public final ToggleButton outlineRgb = new ToggleButton(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 379);
    public final Slider outlineRadius = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 411, false)
            .setWidth(192)
            .setHeight(8)
            .setMinMax(12, 60);
    public final Slider outlineLineWidth = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 439, false)
            .setWidth(180)
            .setHeight(8)
            .setMinMax(1, 7);
    public final Slider outlineFade = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 467, false)
            .setWidth(180)
            .setHeight(8)
            .setMinMax(1, 7);
    public final Slider outlineSpeed = new Slider(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 495, false)
            .setWidth(180)
            .setHeight(8)
            .setMinMax(1, 10);
    public final ColorButton outlineColor = new ColorButton(MainGui.mainGuiPointX + 550, MainGui.mainGuiPointY + 518, MainGui.mainGuiPointX + MainGui.guiWidth + 25, MainGui.mainGuiPointY + 284, 240, 240)
            .setWidth(16)
            .setHeight(16);

    public Customizations() {
        instance = this;
    }

    @Override
    public void updateAfterDeserialization() {
        stateCheckbox
                .addButtonListener((StateEnableListener) button -> register())
                .addButtonListener((StateDisableListener) button -> unregister());
    }
}
