package com.gromit.gromitmod.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.annotation.Savable;
import com.gromit.gromitmod.interfaces.Savables;
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
    private final HashSet<Savables> savables = new HashSet<>();
    private final Set<Class<?>> moduleSet = reflections.getTypesAnnotatedWith(Module.class);
    private final Set<Class<?>> savableSet = reflections.getTypesAnnotatedWith(Savable.class);
    private final String moduleFolder = gromitMod.getModuleFolder();
    private final String savableFolder = gromitMod.getPersistFolder();

    public JsonHandler() {
        readModules();
        readSavables();
    }

    private void readModules() {
        for (Class<?> clazz : moduleSet) {
            File moduleFile = new File(moduleFolder, clazz.getAnnotation(Module.class).moduleName() + ".json");
            if (!moduleFile.exists()) {
                try {
                    moduleFile.createNewFile();
                    AbstractModule module = (AbstractModule) clazz.newInstance();
                    modules.add(module);
                } catch (InstantiationException | IllegalAccessException | IOException e) {throw new RuntimeException(e);}
            } else {
                try {
                    FileReader fileReader = new FileReader(moduleFile);
                    AbstractModule module = (AbstractModule) gson.fromJson(fileReader, clazz);
                    fileReader.close();
                    if (module.isState()) module.register();
                    module.updateAfterDeserialization();
                    modules.add(module);
                } catch (IOException e) {throw new RuntimeException(e);}
            }
        }
    }

    private void readSavables() {
        for (Class<?> clazz : savableSet) {
            File savable = new File(savableFolder, clazz.getAnnotation(Savable.class).savableName() + ".json");
            if (!savable.exists()) {
                try {
                    savable.createNewFile();
                    Savables savables = (Savables) clazz.newInstance();
                    this.savables.add(savables);
                } catch (InstantiationException | IllegalAccessException | IOException e) {throw new RuntimeException(e);}
            } else {
                try {
                    FileReader fileReader = new FileReader(savable);
                    Savables savables = (Savables) gson.fromJson(fileReader, clazz);
                    fileReader.close();
                    savables.updateAfterDeserialization();
                    this.savables.add(savables);
                } catch (IOException e) {throw new RuntimeException(e);}
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
            } catch (IOException e) {throw new RuntimeException(e);}
        }
        for (Savables savables : this.savables) {
            File savableFile = new File(savableFolder, savables.getClass().getAnnotation(Savable.class).savableName() + ".json");
            try {
                FileWriter fileWriter = new FileWriter(savableFile);
                gson.toJson(savables, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
    }
}