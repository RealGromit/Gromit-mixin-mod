package com.gromit.gromitmod.utils.schematic;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Savable;
import com.gromit.gromitmod.gui.button.SchematicButton;
import com.gromit.gromitmod.gui.schematica.SchematicLoadGui;
import com.gromit.gromitmod.interfaces.Savables;
import com.gromit.gromitmod.utils.fontrenderer.FontManager;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Savable(savableName = "SchematicUtils")
public class SchematicUtils implements Savables {

    @Getter private static SchematicUtils instance;
    private transient SchematicLoadGui schematicLoadGui;
    private transient final GromitMod gromitMod = GromitMod.getInstance();
    private final HashMap<String, TreeMap<Integer, PlayerData>> recordMap = new HashMap<>();

    public SchematicUtils() {
        instance = this;
    }

    public void updateSchematics() {
        schematicLoadGui = SchematicLoadGui.getInstance();
        iterateFolders(new File(gromitMod.getSchematicFolder()));
    }

    @Override
    public void updateAfterDeserialization() {
        instance = this;
    }

    private void iterateFolders(File file) {
        if (!file.isDirectory()) return;
        File[] schematics = file.listFiles();
        if (schematics == null || schematics.length == 0) return;

        List<SchematicButton> directory = schematicLoadGui.getDirectoryMap().get(file.getName());
        List<SchematicButton> schematicButtons = new ArrayList<>();

        for (File schematic : schematics) {
            boolean isDirectory = schematic.isDirectory();
            if (!getFileExtension(schematic).equalsIgnoreCase("schematic") && !isDirectory) continue;
            SchematicButton schematicButton = null;
            if (directory != null) {
                boolean schematicExists = false;
                for (SchematicButton button : directory) {
                    if (button.getFile().getName().equalsIgnoreCase(schematic.getName())) {
                        button.setFile(schematic);
                        schematicExists = true;
                    }
                }
                if (!schematicExists) {
                    if (isDirectory) directory.add(schematicButton = new SchematicButton(FontManager.getTitleSize(), 47, 1, schematic, true)
                            .setButtonText("./" + StringUtils.substringBefore(schematic.getName(), ".schematic")));
                    else directory.add(schematicButton = new SchematicButton(FontManager.getTitleSize(), 47, 1, schematic, false)
                            .setButtonText(StringUtils.substringBefore(schematic.getName(), ".schematic")));
                }
            }
            else {
                if (isDirectory) schematicButtons.add(schematicButton = new SchematicButton(FontManager.getTitleSize(), 47, 1, schematic, true)
                        .setButtonText("./" + StringUtils.substringBefore(schematic.getName(), ".schematic")));
                else schematicButtons.add(schematicButton = new SchematicButton(FontManager.getTitleSize(), 47, 1, schematic, false)
                        .setButtonText(StringUtils.substringBefore(schematic.getName(), ".schematic")));
            }
            if (isDirectory) iterateFolders(schematic);
            else {
                recordMap.putIfAbsent(schematic.getName(), new TreeMap<>());
                if (schematicButton != null) schematicButton.setPlayerDataMap(recordMap.get(schematic.getName()));
            }
        }
        schematicLoadGui.getDirectoryMap().putIfAbsent(file.getAbsolutePath(), schematicButtons);
    }

    private String getFileExtension(File file) {
        if (file == null) return "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
