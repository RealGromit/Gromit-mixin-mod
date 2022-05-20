package com.gromit.gromitmod;

import com.gromit.gromitmod.annotation.Module;
import com.gromit.gromitmod.module.AbstractModule;
import jline.internal.Nullable;
import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.List;

public class Sqlite {

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:mods/gromitmod/database.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        Connection connection = getConnection();
        if (connection == null) return;

        try {
            PreparedStatement createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS modules (modulename VARCHAR PRIMARY KEY, module BYTEA)");
            createTable.executeUpdate();
            createTable.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection(connection);
    }

    public static void writeModules(List<AbstractModule> modules) {
        Connection connection = getConnection();
        if (connection == null) return;

        try {
            PreparedStatement writeGui = connection.prepareStatement("INSERT INTO modules (modulename, module) VALUES (?, ?) ON CONFLICT (modulename) DO UPDATE SET module=?");
            for (AbstractModule module : modules) {
                if (module.getClass().getAnnotation(Module.class) == null) continue;
                writeGui.setString(1, module.getClass().getAnnotation(Module.class).moduleName());
                writeGui.setBytes(2, SerializationUtils.serialize(module));
                writeGui.setBytes(3, SerializationUtils.serialize(module));
                writeGui.addBatch();
            }
            writeGui.executeBatch();
            writeGui.close();
        } catch (SQLException e) {throw new RuntimeException(e);}
        closeConnection(connection);
    }

    @Nullable
    public static void readModules(List<AbstractModule> modules, List<String> moduleNames) {
        Connection connection = getConnection();
        if (connection == null) return;

        try {
            PreparedStatement readModules = connection.prepareStatement("SELECT * FROM modules");
            ResultSet resultSet = readModules.executeQuery();

            while (resultSet.next()) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(resultSet.getBytes(2));
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    AbstractModule module = (AbstractModule) objectInputStream.readObject();
                    if (module.isState()) module.register();
                    module.updateAfterDeserialization();
                    modules.add(module);
                    moduleNames.add(resultSet.getString(1));
                    objectInputStream.close();
                    byteArrayInputStream.close();
                } catch (IOException | ClassNotFoundException e) {throw new RuntimeException(e);}
            }
            resultSet.close();
            readModules.close();
        } catch (SQLException e) {throw new RuntimeException(e);}
        closeConnection(connection);
    }
}