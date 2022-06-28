package com.gromit.gromitmod.gui.module.fps;

import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.fps.Tnt;
import lombok.Getter;

import java.awt.*;

public class TntGui extends FpsModuleGui {

    @Getter private static TntGui instance;

    private final ToggleButton tntFlash = Tnt.getInstance().tntFlash;
    private final ToggleButton tntXRAY = Tnt.getInstance().tntXRay;
    private final ToggleButton tntSwell = Tnt.getInstance().tntSwell;
    private final ToggleButton tntFuseLabel = Tnt.getInstance().tntFuseLabel;
    private final ToggleButton tntDropShadow = Tnt.getInstance().tntDropShadow;
    private final ToggleButton tntSmokeParticle = Tnt.getInstance().tntSmokeParticle;
    private final ToggleButton tntDisable = Tnt.getInstance().tntDisable;
    private final ToggleButton tntMinimal = Tnt.getInstance().tntMinimal;
    private final Slider tntLimiter = Tnt.getInstance().tntLimiter;
    private final ToggleButton tntLimiterState = Tnt.getInstance().tntLimiterState;

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(tntFlash);
        buttonList.add(tntXRAY);
        buttonList.add(tntSwell);
        buttonList.add(tntFuseLabel);
        buttonList.add(tntDropShadow);
        buttonList.add(tntSmokeParticle);
        buttonList.add(tntDisable);
        buttonList.add(tntMinimal);
        buttonList.add(tntLimiter);
        buttonList.add(tntLimiterState);
        tnt.setState(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        title.drawString("Tnt", mainGuiPointX + 272, mainGuiPointY + 156, Color.WHITE.getRGB());
        normal.drawString("Tnt Flash", mainGuiPointX + 272, mainGuiPointY + 200, Color.WHITE.getRGB());
        normal.drawString("Tnt Xray", mainGuiPointX + 272, mainGuiPointY + 228, Color.WHITE.getRGB());
        normal.drawString("Tnt Swell", mainGuiPointX + 272, mainGuiPointY + 256, Color.WHITE.getRGB());
        normal.drawString("Fuse Label", mainGuiPointX + 272, mainGuiPointY + 284, Color.WHITE.getRGB());
        normal.drawString("Drop Shadow", mainGuiPointX + 272, mainGuiPointY + 312, Color.WHITE.getRGB());
        normal.drawString("Smoke Particle", mainGuiPointX + 272, mainGuiPointY + 340, Color.WHITE.getRGB());
        normal.drawString("Minimal Tnt", mainGuiPointX + 272, mainGuiPointY + 368, Color.WHITE.getRGB());
        normal.drawString("Disable Tnt", mainGuiPointX + 272, mainGuiPointY + 396, Color.WHITE.getRGB());
        normal.drawString("Tnt Limiter:", mainGuiPointX + 272, mainGuiPointY + 424, Color.WHITE.getRGB());
        tntLimiter.drawButton(mouseX, mouseY);
        normal.drawString(tntLimiter.buttonName, mainGuiPointX + 376, mainGuiPointY + 424, Color.WHITE.getRGB());
        tntFlash.drawButton(mouseX, mouseY);
        tntXRAY.drawButton(mouseX, mouseY);
        tntSwell.drawButton(mouseX, mouseY);
        tntFuseLabel.drawButton(mouseX, mouseY);
        tntDropShadow.drawButton(mouseX, mouseY);
        tntSmokeParticle.drawButton(mouseX, mouseY);
        tntDisable.drawButton(mouseX, mouseY);
        tntMinimal.drawButton(mouseX, mouseY);
        tntLimiterState.drawButton(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {tnt.setState(false);}

    @Override
    protected void setInstance() {
        instance = this;
    }
}
