package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ChangeableButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.render.ExplosionBox;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;

import java.awt.*;

public class ExplosionBoxGui extends CrumbsModuleGui {

    private static final ChangeableButton stateButton = new ChangeableButton(7, 0, 0, 4, "disabled", "enabled",
            () -> ExplosionBox.getInstance().register(),
            () -> ExplosionBox.getInstance().unregister());

    public static final Slider slider = new Slider(8, 0, 0, 100, 2, "", 0, 20, 100);

    public ExplosionBoxGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        stateButton.updateButton(mainGuiPointX + 82, mainGuiPointY + 48, guiScale);
        slider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 70, guiScale);
        buttonList.add(slider);
        buttonList.add(stateButton);
        explosionBox.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtil.title.drawString("Explosion Box", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontUtil.normal.drawString("State:", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontUtil.normal.drawString("Box timeout in seconds:", mainGuiPointX + 68, mainGuiPointY + 65, Color.WHITE.getRGB());
        stateButton.drawButton(minecraft, mouseX, mouseY);
        slider.drawButton(minecraft, mouseX, mouseY);
        FontUtil.normal.drawString(slider.displayString, mainGuiPointX + 122, mainGuiPointY + 65, Color.WHITE.getRGB());
    }

    @Override
    public void onGuiClosed() {
        explosionBox.setState(false);
    }
}
