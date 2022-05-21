package com.gromit.gromitmod.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.module.AbstractModule;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class ModuleHandler {

    // Change capacity of array according to amount of modules
    private final AbstractModule[] modules = new AbstractModule[1];
    private final Reflections reflections = new Reflections("com.gromit.gromitmod");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Set<Class<?>> classSet;
    private final String jsonFolder;

    public ModuleHandler(GromitMod gromitMod) {
        classSet = reflections.getTypesAnnotatedWith(Module.class);
        jsonFolder = gromitMod.getJsonFolder();
        readModules();
    }

    private void readModules() {
        byte i = 0;
        for (Class<?> clazz : classSet) {
            File moduleFile = new File(jsonFolder, clazz.getAnnotation(Module.class).moduleName() + ".json");
            if (!moduleFile.exists()) {
                try {
                    moduleFile.createNewFile();
                    AbstractModule module = (AbstractModule) clazz.newInstance();
                    modules[i] = module;
                } catch (InstantiationException | IllegalAccessException | IOException e) {throw new RuntimeException(e);}
            } else {
                try {
                    FileReader fileReader = new FileReader(moduleFile);
                    AbstractModule module = (AbstractModule) gson.fromJson(fileReader, clazz);
                    fileReader.close();
                    if (module.isState()) module.register();
                    module.updateAfterDeserialization();
                    modules[i] = module;
                } catch (IOException e) {throw new RuntimeException(e);}
            } i++;
        }
    }

    public void writeModules() {
        for (AbstractModule module : modules) {
            File moduleFile = new File(jsonFolder, module.getClass().getAnnotation(Module.class).moduleName() + ".json");
            try {
                FileWriter fileWriter = new FileWriter(moduleFile);
                gson.toJson(module, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
    }
}