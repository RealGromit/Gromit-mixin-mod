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
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class MainGui extends AbstractGui {

    @Getter private static MainGui instance;
    public static final int guiWidth = 1080, guiHeight = 600;
    public static final int mainGuiPointX = 420, mainGuiPointY = 240;

    private static final ResourceLocation gromit = new ResourceLocation("astrix", "gromit.png");

    protected static final TextButton mods = new TextButton(normal, mainGuiPointX + 260, mainGuiPointY + 64)
            .setButtonText("Mods")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getModsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getModsModuleGui());
                else minecraft.displayGuiScreen(ModsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton fps = new TextButton(normal, mainGuiPointX + 460, mainGuiPointY + 64)
            .setButtonText("Fps")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getFpsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getFpsModuleGui());
                else minecraft.displayGuiScreen(FpsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton crumbs = new TextButton(normal, mainGuiPointX + 640, mainGuiPointY + 64)
            .setButtonText("Crumbs")
            .addButtonListener((ClickEnableListener) button -> {
                if (GlobalSaver.getCrumbsModuleGui() != null) minecraft.displayGuiScreen(GlobalSaver.getCrumbsModuleGui());
                else minecraft.displayGuiScreen(CrumbsModuleGui.getInstance());
            })
            .addButtonListener((ClickDisableListener) button -> minecraft.displayGuiScreen(MainGui.getInstance()));

    protected static final TextButton other = new TextButton(normal, (int) (mainGuiPointX + 920 - normal.getWidth("Other")), mainGuiPointY + 64)
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

        RenderUtils.drawRoundedRectangle1(mainGuiPointX, mainGuiPointY, guiWidth, guiHeight, 1, 30, ColorUtils.RGBA2Integer(54, 54, 54, 225));
        RenderUtils.drawRoundedRectangleOutlineRgb(Display.getWidth() / 2f, Display.getHeight() / 2f, guiWidth, guiHeight, 9, 30, Color.WHITE.getRGB());
        RenderUtils.drawLine(mainGuiPointX + 180, mainGuiPointY + 92, 820, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawCircleOutlineRgb(511, 315, 48, 7, Color.WHITE.getRGB());
        minecraft.getTextureManager().bindTexture(gromit);
        RenderUtils.drawTexture(mainGuiPointX + 17, mainGuiPointY + 23, 150, 100, 0, 0, 1, 1);
        mods.drawButton(mouseX, mouseY);
        fps.drawButton(mouseX, mouseY);
        crumbs.drawButton(mouseX, mouseY);
        other.drawButton(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    protected void setInstance() {instance = this;}
}
