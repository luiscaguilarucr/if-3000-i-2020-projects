package edu.ucr.rp.programacion2.proyecto.business_rules.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class IDGeneratorPersistence implements Persistence<Integer, Integer> {
    //  Variables  \\
    private String path;
    private final String suffix = ".json";
    private final JsonUtil jsonUtil = new JsonUtil();
    //  Constructor  \\

    public IDGeneratorPersistence(String inventoryName) {
        this.path = "files/inventories/" + inventoryName + "/";
    }
    //  Methods  \\
    @Override
    public boolean write(Integer id) {
        jsonUtil.toFile(new File(path +"config" + suffix), id);
        return true;
    }

    @Override
    public Integer read() {
        File file = new File(path +"/config" + suffix);
        if (file.exists()) {
            try {
                return jsonUtil.asObject(file.toURI().toURL(), Integer.class);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public boolean delete(Integer o) {
        try {
            FileUtils.forceDelete(new File(path));
            return true;
        } catch (IOException e) {
            return false;
        }

    }
}
