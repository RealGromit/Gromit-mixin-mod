package com.gromit.gromitmod.handler.buttonhandler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ScrollableButton;
import com.gromit.gromitmod.handler.GuiHandler;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.Minecraft;

public class ModuleGuiButtons {

    private static final Minecraft minecraft = GromitMod.INSTANCE.getMinecraft();

    private static final ScrollableButton rangeCalc = new ScrollableButton(2, 0, 0, (int) (FontUtil.normal.getStringWidth("Range Calc") / 1.9), 4, "Range Calc", 1,
            () -> minecraft.displayGuiScreen(GuiHandler.getRangeCalcModuleGui()));

    public static ScrollableButton getRangeCalc() {
        return rangeCalc;
    }
}
