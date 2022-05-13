package com.gromit.gromitmod.gui.module.crumbs;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.gui.button.ChangeableButton;
import com.gromit.gromitmod.gui.slider.Slider;
import com.gromit.gromitmod.module.render.ExplosionBox;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class ExplosionBoxGui extends CrumbsModuleGui {

    private static ExplosionBoxGui instance;
    private static final ChangeableButton stateButton = new ChangeableButton(7, 0, 0, 4, "disabled", "enabled",
            (button) -> {
                ExplosionBox.getInstance().register();
                button.setState(true);
            },
            (button) -> {
                ExplosionBox.getInstance().unregister();
                button.setState(false);
            });

    public static final ChangeableButton precisionButton = new ChangeableButton(8, 0, 0, 4, "low", "high",
            (button) -> button.setState(true),
            (button) -> button.setState(false));

    public static final Slider slider = new Slider(9, 0, 0, 100, 2, "", 0, 20, 100);

    public ExplosionBoxGui(GromitMod gromitMod) {
        super(gromitMod);
    }

    @Override
    public void initGui() {
        super.initGui();

        stateButton.updateButton(mainGuiPointX + 82, mainGuiPointY + 48, guiScale);
        precisionButton.updateButton(mainGuiPointX + 90, mainGuiPointY + 58, guiScale);
        slider.updateSlider(mainGuiPointX + 68, mainGuiPointY + 80, guiScale);
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

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glShadeModel(GL_SMOOTH);
        RenderUtils.worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        RenderUtils.worldRenderer.pos(mainGuiPointX + 68, mainGuiPointY + 90, 0).color(255, 255, 255, 255).endVertex();
        RenderUtils.worldRenderer.pos(mainGuiPointX + 68, mainGuiPointY + 140, 0).color(0, 0, 0, 255).endVertex();
        RenderUtils.worldRenderer.pos(mainGuiPointX + 120, mainGuiPointY + 140, 0).color(0, 0, 0, 255).endVertex();
        RenderUtils.worldRenderer.pos(mainGuiPointX + 120, mainGuiPointY + 90, 0).color(255, 0, 0, 255).endVertex();
        RenderUtils.tessellator.draw();
        glShadeModel(GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
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
