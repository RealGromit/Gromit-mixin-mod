package com.gromit.gromitmod.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.annotation.Persist;
import com.gromit.gromitmod.interfaces.Savable;
import com.gromit.gromitmod.module.AbstractModule;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JsonHandler {

    private final GromitMod gromitMod = GromitMod.getInstance();

    private final Reflections reflections = new Reflections("com.gromit.gromitmod");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final HashSet<AbstractModule> modules = new HashSet<>();
    private final HashSet<Savable> savables = new HashSet<>();
    private final Set<Class<?>> moduleSet = reflections.getTypesAnnotatedWith(Module.class);
    private final Set<Class<?>> persistSet = reflections.getTypesAnnotatedWith(Persist.class);
    private final String moduleFolder = gromitMod.getModuleFolder();
    private final String persistFolder = gromitMod.getPersistFolder();

    public JsonHandler() {
        readModules();
        readPersists();
    }

    private void readModules() {
        for (Class<?> clazz : moduleSet) {
            File moduleFile = new File(moduleFolder, clazz.getAnnotation(Module.class).moduleName() + ".json");
            if (!moduleFile.exists()) {
                try {
                    moduleFile.createNewFile();
                    AbstractModule module = (AbstractModule) clazz.newInstance();
                    modules.add(module);
                } catch (InstantiationException | IllegalAccessException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    FileReader fileReader = new FileReader(moduleFile);
                    AbstractModule module = (AbstractModule) gson.fromJson(fileReader, clazz);
                    fileReader.close();
                    if (module.isState()) module.register();
                    module.updateAfterDeserialization();
                    modules.add(module);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void readPersists() {
        for (Class<?> clazz : persistSet) {
            File persistFile = new File(persistFolder, clazz.getAnnotation(Persist.class).persistName() + ".json");
            if (!persistFile.exists()) {
                try {
                    persistFile.createNewFile();
                    Savable savable = (Savable) clazz.newInstance();
                    savables.add(savable);
                } catch (InstantiationException | IllegalAccessException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    FileReader fileReader = new FileReader(persistFile);
                    Savable savable = (Savable) gson.fromJson(fileReader, clazz);
                    fileReader.close();
                    savable.updateAfterDeserialization();
                    savables.add(savable);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void writeModules() {
        for (AbstractModule module : modules) {
            File moduleFile = new File(moduleFolder, module.getClass().getAnnotation(Module.class).moduleName() + ".json");
            try {
                FileWriter fileWriter = new FileWriter(moduleFile);
                gson.toJson(module, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for (Savable savable : savables) {
            File persistFile = new File(persistFolder, savable.getClass().getAnnotation(Persist.class).persistName() + ".json");
            try {
                FileWriter fileWriter = new FileWriter(persistFile);
                gson.toJson(savable, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}