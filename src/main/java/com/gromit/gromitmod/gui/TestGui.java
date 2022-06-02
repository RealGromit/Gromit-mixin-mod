package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.gui.button.GromitButton;
import com.gromit.gromitmod.gui.button.listener.EndHoverListener;
import com.gromit.gromitmod.gui.button.listener.HoverListener;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import lombok.Getter;

import java.awt.Color;

public class TestGui extends AbstractGui {

    @Getter private static TestGui instance;
    private final GromitButton testButton = new GromitButton(FontUtil.normal, 30, 30)
            .setButtonText("Cool")
            .setColor(Color.WHITE.getRGB())
            .addButtonListener((EndHoverListener) button -> button.setColor(Color.WHITE.getRGB()))
            .addButtonListener((HoverListener) button -> button.setColor(ColorUtils.getRGB()));

    public TestGui() {instance = this;}

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(testButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        testButton.drawButton(mouseX, mouseY);
    }
}
