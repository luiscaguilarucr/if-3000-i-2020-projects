package edu.ucr.rp.programacion2.proyecto.business_rules.io;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InventoryPersistence implements Persistence<String>{
    private String path = System.getProperty("s");

    @Override
    public boolean save(String name) {
        return false;
    }

    @Override
    public String get() {
        return null;
    }

    @Override
    public boolean delete() {
        return false;
    }
    File file = new File("inventories");

    public InventoryPersistence(File file) {
        this.file = file;
    }
}
