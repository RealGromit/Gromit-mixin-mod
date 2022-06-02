package com.gromit.gromitmod.utils.schematic;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Persist;
import com.gromit.gromitmod.gui.button.SchematicButton;
import com.gromit.gromitmod.gui.schematica.SchematicLoadGui;
import com.gromit.gromitmod.interfaces.Savable;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Persist(persistName = "SchematicUtils")
public class SchematicUtils implements Savable {

    private static SchematicUtils instance;
    private transient SchematicLoadGui schematicLoadGui;
    public transient final File schematicFolder = new File("./schematics/");
    private transient final GromitMod gromitMod = GromitMod.getInstance();
    private final HashMap<String, List<PlayerData>> recordMap = new HashMap<>();

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

        List<SchematicButton> directory = schematicLoadGui.directoryMap.get(file.getName());
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
                    if (isDirectory) directory.add(schematicButton = new SchematicButton(gromitMod.getNewButtonId(), 6, "./" + StringUtils.substringBefore(schematic.getName(), ".schematic"), schematic, true));
                    else directory.add(schematicButton = new SchematicButton(gromitMod.getNewButtonId(), 6, StringUtils.substringBefore(schematic.getName(), ".schematic"), schematic, false));
                }
            }
            if (directory == null) {
                if (isDirectory) schematicButtons.add(schematicButton = new SchematicButton(gromitMod.getNewButtonId(), 6, "./" + StringUtils.substringBefore(schematic.getName(), ".schematic"), schematic, true));
                else schematicButtons.add(schematicButton = new SchematicButton(gromitMod.getNewButtonId(), 6, StringUtils.substringBefore(schematic.getName(), ".schematic"), schematic, false));
            }
            if (isDirectory) iterateFolders(schematic);
            if (!isDirectory) {
                List<PlayerData> playerDataList = new ArrayList<>();
                recordMap.putIfAbsent(schematic.getName(), playerDataList);
                if (schematicButton != null) schematicButton.playerDataList = playerDataList;
            }
        }
        schematicLoadGui.directoryMap.putIfAbsent(file.getAbsolutePath(), schematicButtons);
    }

    private String getFileExtension(File file) {
        if (file == null) return "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public static SchematicUtils getInstance() {
        return instance;
    }
}
