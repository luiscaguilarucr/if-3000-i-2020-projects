package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Configuration;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ConfigurationPersistence implements Persistence<Integer, Integer> {
    //  Variables  \\
    private String path;
    private final String suffix = ".json";
    private final JsonUtil jsonUtil = new JsonUtil();
    //  Constructor  \\

    public ConfigurationPersistence(String inventoryName) {
        this.path = "files/inventories/" + inventoryName + "/";
    }
    //  Methods  \\
    @Override
    public boolean write(Integer id) {
        Configuration config = new Configuration(id);
        jsonUtil.toFile(new File(path +"config" + suffix), config);
        return true;
    }

    @Override
    public Integer read() {
        File file = new File(path +"/config" + suffix);
        if (file.exists()) {
            try {
                Configuration configuration = jsonUtil.asObject(file.toURI().toURL(), Configuration.class);
                return configuration.getId();
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
