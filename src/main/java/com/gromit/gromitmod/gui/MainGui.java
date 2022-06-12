package com.gromit.gromitmod.gui;

import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.listener.ClickDisableListener;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.module.CrumbsModuleGui;
import com.gromit.gromitmod.gui.module.FpsModuleGui;
import com.gromit.gromitmod.gui.module.ModsModuleGui;
import com.gromit.gromitmod.gui.module.OtherModuleGui;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.GlobalSaver;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;

public class MainGui extends AbstractGui {

    @Getter private static MainGui instance;
    public static final int guiWidth = 270, guiHeight = 150;
    public static final int mainGuiPointX = (480 - guiWidth) / 2;
    public static final int mainGuiPointY = (270 - guiHeight) / 2;

    private static final ResourceLocation bounds = new ResourceLocation("astrix", "border.png");
    private static final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    protected static final TextButton mods = new TextButton(FontUtil.normal, mainGuiPointX + 65, mainGuiPointY + 16)
            .setButtonText("Mods")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getModsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getModsModuleGui());
                else minecraft.displayGuiScreen(ModsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton fps = new TextButton(FontUtil.normal, mainGuiPointX + 115, mainGuiPointY + 16)
            .setButtonText("Fps")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getFpsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getFpsModuleGui());
                else minecraft.displayGuiScreen(FpsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton crumbs = new TextButton(FontUtil.normal, mainGuiPointX + 160, mainGuiPointY + 16)
            .setButtonText("Crumbs")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getCrumbsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getCrumbsModuleGui());
                else minecraft.displayGuiScreen(CrumbsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton other = new TextButton(FontUtil.normal, (int) (mainGuiPointX + 230 - FontUtil.normal.getStringWidth("Other") / 2), mainGuiPointY + 16)
            .setButtonText("Other")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getOtherModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getOtherModuleGui());
                else minecraft.displayGuiScreen(OtherModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    public MainGui() {
        setInstance();
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(mods);
        buttonList.add(fps);
        buttonList.add(crumbs);
        buttonList.add(other);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        minecraft.getTextureManager().bindTexture(bounds);
        RenderUtils.drawTextureColor(mainGuiPointX - 1, mainGuiPointY - 1, guiWidth + 2, guiHeight + 2, 0, 0, 1, 1, ColorUtils.getRed(), ColorUtils.getGreen(), ColorUtils.getBlue(), 255);
        RenderUtils.drawRectangle(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, ColorUtils.RGBA2Integer(54, 54, 54, 225));
        RenderUtils.drawLine(mainGuiPointX + 45, mainGuiPointY + 23, 205, 0, 3, false, 255, 255, 255, 255);
        minecraft.getTextureManager().bindTexture(gromit);
        RenderUtils.drawCircleOutline(mainGuiPointX + 21, mainGuiPointY + 18, 11, 3, 200, ColorUtils.getRGB(255));
        RenderUtils.drawTexture(mainGuiPointX + 5, mainGuiPointY + 5, 33, 25, 0, 0, 1, 1);
        mods.drawButton(mouseX, mouseY);
        fps.drawButton(mouseX, mouseY);
        crumbs.drawButton(mouseX, mouseY);
        other.drawButton(mouseX, mouseY);
    }

    protected void setInstance() {instance = this;}
}
