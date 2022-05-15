package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ChangeableButton;
import com.gromit.gromitmod.gui.button.ColorButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.render.ExplosionBox;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class ExplosionBoxGui extends CrumbsModuleGui {

    private static ExplosionBoxGui instance;
    public static final ColorButton colorButton = new ColorButton(7, 0, 0, 6, 6, 0, 0, 60, 60);
    private static final ChangeableButton stateButton = new ChangeableButton(8, 0, 0, 4, "disabled", "enabled",
            (button) -> {
                ExplosionBox.getInstance().register();
                button.setState(true);
            },
            (button) -> {
                ExplosionBox.getInstance().unregister();
                button.setState(false);
            });

    public static final ChangeableButton precisionButton = new ChangeableButton(9, 0, 0, 4, "low", "high",
            (button) -> button.setState(true),
            (button) -> button.setState(false));

    public static final Slider slider = new Slider(10, 0, 0, 100, 2, "", 0, 20, 100);

    public ExplosionBoxGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        colorButton.updateColorButton(mainGuiPointX + 68, mainGuiPointY + 90, mainGuiPointX + 200, mainGuiPointY + 40, guiScale);
        stateButton.updateButton(mainGuiPointX + 82, mainGuiPointY + 48, guiScale);
        precisionButton.updateButton(mainGuiPointX + 90, mainGuiPointY + 58, guiScale);
        slider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 80, guiScale);
        buttonList.add(colorButton);
        buttonList.add(stateButton);
        buttonList.add(precisionButton);
        buttonList.add(slider);
        explosionBox.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Explosion Box", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("State:", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Precision:", mainGuiPointX + 68, mainGuiPointY + 60, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box timeout in seconds:", mainGuiPointX + 68, mainGuiPointY + 75, Color.WHITE.getRGB());
        stateButton.drawButton(minecraft, mouseX, mouseY);
        precisionButton.drawButton(minecraft, mouseX, mouseY);
        slider.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(slider.displayString, mainGuiPointX + 122, mainGuiPointY + 75, Color.WHITE.getRGB());
        colorButton.drawButton(minecraft, mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        explosionBox.setState(false);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }

    public static ExplosionBoxGui getInstance() {
        return instance;
    }
}
