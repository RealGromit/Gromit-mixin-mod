package com.gromit.gromitmod.gui.module.fps;

import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.module.fps.FallingBlock;
import lombok.Getter;

import java.awt.Color;

public class FallingBlockGui extends FpsModuleGui {

    @Getter private static FallingBlockGui instance;

    private final ToggleButton fallingBlockXRay = FallingBlock.getInstance().fallingBlockXRay;
    private final ToggleButton fallingBlockDropShadow = FallingBlock.getInstance().fallingBlockDropShadow;
    private final ToggleButton fallingBlockDisable = FallingBlock.getInstance().fallingBlockDisable;
    private final ToggleButton fallingBlockOptimize = FallingBlock.getInstance().fallingBlockOptimize;

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(fallingBlockXRay);
        buttonList.add(fallingBlockDropShadow);
        buttonList.add(fallingBlockDisable);
        buttonList.add(fallingBlockOptimize);
        sand.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        title.drawString("Falling Block", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Falling Block Xray", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Drop Shadow", mainGuiPointX + 272, mainGuiPointY + 228, Color.WHITE.getRGB());
        normal.drawString("Optimize sand", mainGuiPointX + 272, mainGuiPointY + 256, Color.WHITE.getRGB());
        normal.drawString("Disable Falling Block", mainGuiPointX + 272, mainGuiPointY + 284, Color.WHITE.getRGB());
        fallingBlockXRay.drawButton(mouseX, mouseY);
        fallingBlockDropShadow.drawButton(mouseX, mouseY);
        fallingBlockDisable.drawButton(mouseX, mouseY);
        fallingBlockOptimize.drawButton(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {sand.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }
}
