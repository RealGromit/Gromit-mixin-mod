package com.gromit.gromitmod.gui.schematica;

import com.gromit.gromitmod.gui.AbstractGui;
import com.gromit.gromitmod.gui.button.SchematicButton;
import com.gromit.gromitmod.gui.button.TextButton;
import com.gromit.gromitmod.gui.button.listener.ClickEnableListener;
import com.gromit.gromitmod.gui.button.listener.ClickListener;
import com.gromit.gromitmod.utils.ColorUtils;
import com.gromit.gromitmod.utils.RenderUtils;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import lombok.Getter;

import java.awt.*;
import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SchematicLoadGui extends AbstractGui {

    @Getter private static SchematicLoadGui instance;
    @Getter private final HashMap<String, List<SchematicButton>> directoryMap = new HashMap<>();
    @Getter private List<SchematicButton> schematicButtons;
    private File lastDirectory;
    private final File schematicFolder = new File(minecraft.mcDataDir.getPath() + "/schematics");

    private final TextButton backButton = new TextButton(FontManager.getNormalSize(), 47, 33)
            .setButtonText("<- back")
            .addButtonListener((ClickEnableListener) button -> button.setState(false))
            .addButtonListener((ClickListener) button -> changeDirectory(lastDirectory.getParentFile()));

    public SchematicLoadGui() {
        instance = this;
    }

    @Override
    public void initGui() {
        super.initGui();
        int buttonY = 41;
        backButton.setEnabled(true);

        if (lastDirectory != null) {
            schematicButtons = directoryMap.get(lastDirectory.getAbsolutePath());
            if (lastDirectory.getAbsolutePath().equals(schematicFolder.getAbsolutePath())) backButton.setEnabled(false);
        }
        else {
            schematicButtons = directoryMap.get(schematicFolder.getAbsolutePath());
            backButton.setEnabled(false);
        }
        schematicButtons.sort(Comparator.comparing(SchematicButton::getButtonText).thenComparing(SchematicButton::isDirectory));

        if (backButton.isEnabled()) buttonList.add(backButton);
        for (SchematicButton schematicButton : schematicButtons) {
            schematicButton.setY(buttonY);
            buttonList.add(schematicButton);
            buttonList.add(schematicButton.getRecordButton());
            buttonList.add(schematicButton.getClearButton());
            buttonList.add(schematicButton.getPlayButton());
            buttonList.add(schematicButton.getAutoPlay());
            buttonY += 8;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontManager.getBiggerTitleSize().drawString("Load Schematic", 240 - FontManager.getBiggerTitleSize().getWidth("Load schematic") / 2, 10, Color.WHITE.getRGB());
        RenderUtils.drawLine(30, 20, 420, 0, 4, false, 255, 255, 255, 255);

        RenderUtils.drawRectangle(45, 30, 240, 200, ColorUtils.RGBA2Integer(0, 0, 0, 60));
        RenderUtils.drawLine(45, 30.3f, 240, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(45, 30, 0, 200, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(45, 229.7f, 240, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(285, 30, 0, 200, 3, false, 255, 255, 255, 255);

        RenderUtils.drawRectangle(300, 30, 135, 60, ColorUtils.RGBA2Integer(0, 0, 0, 60));
        RenderUtils.drawLine(300, 30.3f, 135, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(300, 30, 0, 60, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(300, 89.7f, 135, 0, 3, false, 255, 255, 255, 255);
        RenderUtils.drawLine(435, 30, 0, 60, 3, false, 255, 255, 255, 255);

        backButton.drawButton(mouseX, mouseY);
        for (SchematicButton schematicButton : schematicButtons) {
            schematicButton.drawButton(mouseX, mouseY);
        }
    }

    public void changeDirectory(File folderPath) {
        int buttonY = 41;
        lastDirectory = folderPath;
        schematicButtons = directoryMap.get(folderPath.getAbsolutePath());
        schematicButtons.sort(Comparator.comparing(SchematicButton::getButtonText).thenComparing(SchematicButton::isDirectory));
        buttonList.clear();

        backButton.setEnabled(!folderPath.getAbsolutePath().equals(schematicFolder.getAbsolutePath()));
        if (backButton.isEnabled()) buttonList.add(backButton);
        for (SchematicButton schematicButton : schematicButtons) {
            schematicButton.setY(buttonY);
            buttonList.add(schematicButton);
            buttonList.add(schematicButton.getRecordButton());
            buttonList.add(schematicButton.getClearButton());
            buttonList.add(schematicButton.getPlayButton());
            buttonList.add(schematicButton.getAutoPlay());
            buttonY += 8;
        }
    }
}
