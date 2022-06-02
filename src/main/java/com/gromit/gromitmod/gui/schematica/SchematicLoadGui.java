package com.gromit.gromitmod.gui.schematica;

import com.gromit.gromitmod.gui.ScalableGui;
import com.gromit.gromitmod.gui.button.SchematicButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontUtil;
import org.lwjgl.input.Mouse;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SchematicLoadGui extends ScalableGui {

    private static SchematicLoadGui instance;
    public final HashMap<String, List<SchematicButton>> directoryMap = new HashMap<>();
    private List<SchematicButton> schematicButtons;
    private File lastDirectory;
    private final File schematicFolder = new File("./schematics");
    public int scroll;
    private boolean renderBackButton = true;

    private final TextButton backButton = new TextButton(gromitMod.getNewButtonId(), 4, "<- back",
            button -> {
                button.setState(false);
                changeDirectory(lastDirectory.getParentFile());
            },
            button -> {
                button.setState(false);
                changeDirectory(lastDirectory.getParentFile());
            });

    public SchematicLoadGui() {
        instance = this;
    }

    @Override
    public void initGui() {
        super.initGui();
        int buttonY = 41;
        renderBackButton = true;

        if (lastDirectory != null) {
            schematicButtons = directoryMap.get(lastDirectory.getAbsolutePath());
            if (lastDirectory.getAbsolutePath().equals(schematicFolder.getAbsolutePath())) renderBackButton = false;
        }
        else {
            schematicButtons = directoryMap.get(schematicFolder.getAbsolutePath());
            renderBackButton = false;
        }
        schematicButtons.sort(Comparator.comparing(SchematicButton::getDisplayString).thenComparing(SchematicButton::isDirectory));

        if (renderBackButton) {
            backButton.updateButton(47, 33, guiScale);
            buttonList.add(backButton);
        }
        for (SchematicButton schematicButton : schematicButtons) {
            schematicButton.updateButton(47, buttonY, guiScale);
            buttonList.add(schematicButton);
            schematicButton.updateButtonList(buttonList);
            buttonY += 8;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        FontUtil.biggerTitle.drawString("Load Schematic", 240 - FontUtil.biggerTitle.getStringWidth("Load schematic") / 4, 10, Color.WHITE.getRGB());
        RenderUtils.drawLine(30, 20, 420, 0, 4, false, 255, 255, 255, 255);

        RenderUtils.drawRectangle(45, 30, 240, 200, 0, 0, 0, 60);
        RenderUtils.drawLine(45, 30.3, 240, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(45, 30, 0, 200, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(45, 229.7, 240, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(285, 30, 0, 200, 3, false, 255, 255, 255, 255);

        RenderUtils.drawRectangle(300, 30, 135, 150, 0, 0, 0, 60);
        RenderUtils.drawLine(300, 30.3, 135, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(300, 30, 0, 150, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(300, 179.7, 135, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(435, 30, 0, 150, 3, false, 255, 255, 255, 255);

        if (renderBackButton) backButton.drawButton(minecraft, mouseX, mouseY);
        for (SchematicButton schematicButton : schematicButtons) {
            schematicButton.drawButton(minecraft, mouseX, mouseY);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        scroll += Mouse.getEventDWheel() / 60;
    }

    public void changeDirectory(File folderPath) {
        int buttonY = 41;
        lastDirectory = folderPath;
        schematicButtons = directoryMap.get(folderPath.getAbsolutePath());
        schematicButtons.sort(Comparator.comparing(SchematicButton::getDisplayString).thenComparing(SchematicButton::isDirectory));
        buttonList.clear();

        renderBackButton = !folderPath.getAbsolutePath().equals(schematicFolder.getAbsolutePath());
        if (renderBackButton) {
            backButton.updateButton(47, 33, guiScale);
            buttonList.add(backButton);
        }
        for (SchematicButton schematicButton : schematicButtons) {
            schematicButton.updateButton(47, buttonY, guiScale);
            buttonList.add(schematicButton);
            schematicButton.updateButtonList(buttonList);
            buttonY += 8;
        }
    }

    public static SchematicLoadGui getInstance() {
        return instance;
    }
}
