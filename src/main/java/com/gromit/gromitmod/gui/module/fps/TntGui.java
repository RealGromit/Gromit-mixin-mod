package com.gromit.gromitmod.gui.module.fps;

import com.gromit.gromitmod.gui.button.ToggleButton;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.module.fps.Tnt;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import lombok.Getter;

import java.awt.*;

public class TntGui extends FpsModuleGui {

    @Getter
    private static TntGui instance;

    private final ToggleButton tntFlash = Tnt.getInstance().tntFlash;
    private final ToggleButton tntXRAY = Tnt.getInstance().tntXRay;
    private final ToggleButton tntSwell = Tnt.getInstance().tntSwell;

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(tntFlash);
        buttonList.add(tntXRAY);
        buttonList.add(tntSwell);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontManager.getTitleSize().drawString("Tnt", mainGuiPointX + 68, mainGuiPointY + 39, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Tnt flash", mainGuiPointX + 68, mainGuiPointY + 50, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Xray Tnt", mainGuiPointX + 68, mainGuiPointY + 57, Color.WHITE.getRGB());
        FontManager.getNormalSize().drawString("Tnt Swell", mainGuiPointX + 68, mainGuiPointY + 64, Color.WHITE.getRGB());
        tntFlash.drawButton(mouseX, mouseY);
        tntXRAY.drawButton(mouseX, mouseY);
        tntSwell.drawButton(mouseX, mouseY);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
