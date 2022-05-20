package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.GromitMod;
import com.gromit.gromitmod.Sqlite;
import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.module.AbstractModule;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleHandler {

    private final GromitMod gromitMod;
    private final List<AbstractModule> modules = new ArrayList<>();
    private final List<String> moduleNames = new ArrayList<>();
    private final Reflections reflections = new Reflections("com.gromit.gromitmod");
    private final Set<Class<?>> classSet;

    public ModuleHandler(GromitMod gromitMod) {
        this.gromitMod = gromitMod;
        classSet = reflections.getTypesAnnotatedWith(Module.class);
        readModules();
    }

    private void readModules() {
        Sqlite.readModules(modules, moduleNames);
        classSet.forEach(clazz -> {
            if (!moduleNames.contains(clazz.getAnnotation(Module.class).moduleName())) {
                try {
                    AbstractModule module = (AbstractModule) clazz.newInstance();
                    modules.add(module);
                } catch (InstantiationException | IllegalAccessException e) {throw new RuntimeException(e);}
            }
        });
    }

    public void writeModules() {
        Sqlite.writeModules(modules);
    }
}