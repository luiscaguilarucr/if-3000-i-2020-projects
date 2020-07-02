package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;
import edu.ucr.rp.programacion2.proyecto.util.builders.CatalogBuilder;
import edu.ucr.rp.programacion2.proyecto.domain.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogSocketPersistence implements CatalogPersistence {

    private String path;
    private final String suffix = ".json";
    private final JsonUtil jsonUtil = new JsonUtil();

    /**
     * It's responsibly is make an catalog persistent.
     * Also can restore catalogs and convert them to objects in memory.
     *
     * @param inventoryName of the list of catalogs
     */
    public CatalogSocketPersistence(String inventoryName) {
        this.path = "files/inventories/" + inventoryName + "/catalogs/";
        verifyCatalogsDir(path);
    }
    private void verifyCatalogsDir(String path){
        File file = new File(path);
        if(!file.exists())
            file.mkdir();
    }
    /**
     * Saves one catalog.
     *
     * @param catalog catalog to save
     * @return {@code true} if the catalog have been saved.{@code false} Otherwise.
     */
    @Override
    public boolean write(Catalog catalog) throws PersistenceException{
        if (catalog == null) return false;                       // Not null
        if (!checkDirectory(catalog.getName())) return false;    // Check dir
        if (!saveConfig(catalog)) return false;                      // Save ID
        if (!saveSchema(catalog)) return false;                  // Save Schema
        return saveItems(catalog);                               // Save Items
    }

    /**
     * Reads and return a list of catalogs linked to one inventory.
     *
     * @return {@code List<Catalog>} List of the catalogs.
     */
    @Override
    public List<Catalog> read() throws PersistenceException{
        return readCatalogs();
    }

    @Override
    public boolean delete(Catalog catalog) throws PersistenceException {
        try {
            FileUtils.forceDelete(new File(path + catalog.getName()));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //  Other Methods  \\

    /**
     * Reads and returns a list of catalogs linked to one inventory.
     * Search all the directories created in the inventory path.
     *
     * @return @return {@code List<Catalog>} List of the catalogs.
     */
    private List<Catalog> readCatalogs() {
        List<Catalog> catalogs = new ArrayList<>();
        String[] catalogNames = readCatalogNames();

        if (catalogNames != null) {
            for (String catalogName : catalogNames) {
                Catalog catalog = buildCatalog(catalogName);
                if (catalog != null)
                    catalogs.add(catalog);
                else
                    System.out.println("Error while reading catalog: " + catalogName);
            }
        }
        return catalogs;
    }

    /**
     * Creates or checks is exists the directory of the catalog.
     *
     * @param name of the directory, that is the same as the catalog.
     * @return {@code true} if the directory have been created or saved.{@code false} Otherwise.
     */
    private boolean checkDirectory(String name) {
        File file = new File(path + name + "/");
        if (file.exists()) return true;
        return file.mkdir();
    }

    /**
     * It's responsible is to save the config of one catalog.
     *
     * @param catalog Catalog that contains one id.
     * @return {@code true} if the catalog's id have been saved.{@code false} Otherwise.
     */
    private boolean saveConfig(Catalog catalog) {
        if (catalog == null) return false;                      // Not null catalog.
        jsonUtil.toFile(new File(path + catalog.getName() + "/config" + suffix), catalog.getConfiguration());
        return true;
    }

    /**
     * It's responsible is to save the schema of one catalog.
     *
     * @param catalog Catalog that contains the schema.
     * @return {@code true} if the catalog's schema have been saved.{@code false} Otherwise.
     */
    private boolean saveSchema(Catalog catalog) {
        if (catalog == null) return false;                      // Not null catalog.
        if (catalog.getSchema() == null) return false;          // Not null schema.
        if (catalog.getSchema().isEmpty()) return false;        // Not empty schema.
        jsonUtil.toFile(new File(path + catalog.getName() + "/schema" + suffix), catalog.getSchema());
        return true;
    }

    /**
     * It's responsible is to save the list of one catalog.
     *
     * @param catalog Catalog that contains the list of items.
     * @return {@code true} if the catalog's items have been saved.{@code false} Otherwise.
     */
    private boolean saveItems(Catalog catalog) {
        if (catalog == null) return false;                      // Not null catalog.
        if (catalog.getItems() == null) return false;           // Not null item list.

        jsonUtil.toFile(new File(path + catalog.getName() + "/items" + suffix), catalog.getItems());
        return true;
    }

    /**
     * Gets the list of catalog's names created as directories in the catalogs path.
     * Gets each directory name, which is the catalog name.
     *
     * @return {@code String[]} List of catalog's names.
     */
    private String[] readCatalogNames() {
        File file = new File(path);
        if (file.exists())
            return file.list();
        return null;
    }

    /**
     * It's responsible of create a Catalog object with the name.
     * With the catalog name, this method can reads it subdirectories.
     *
     * @param catalogName name of the catalog.
     * @return {@code Catalog} built, {@code null} if an error has occurred.
     */
    private Catalog buildCatalog(String catalogName) {
        Configuration config = readConfig(catalogName);  // ID
        List<String> schema = readSchema(catalogName);          // Schema
        List<Map> items = readItems(catalogName);              // Items
        if (config == null) return null;                            // Verify ID
        if (schema == null) return null;                        // Verify Schema
        if (items == null) return null;                         // Verify Items
        CatalogBuilder builder = new CatalogBuilder();          // Build Catalog.
        return builder
                .setConfig(config)
                .withName(catalogName)
                .withSchema(schema)
                .withItems(items)
                .build();
    }

    /**
     * Reads the config file and creates a Configuration object.
     *
     * @param catalogName to get the config.
     * @return {@code Configuration} config of the catalog, {@code null} if an error has occurred.
     */
    private Configuration readConfig(String catalogName) {
        File file = new File(path + catalogName + "/config" + suffix);
        if (file.exists()) {
            try {
                return jsonUtil.asObject(file.toURI().toURL(), Configuration.class);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Reads the schema's file and creates a List object.
     *
     * @param catalogName to get the schema.
     * @return {@code List} schema, {@code null} if an error has occurred.
     */
    private List readSchema(String catalogName) {
        File file = new File(path + catalogName + "/schema" + suffix);
        if (file.exists()) {
            try {
                return jsonUtil.asObject(file.toURI().toURL(), List.class);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Reads the items's file and creates a List object.
     *
     * @param catalogName to get the items.
     * @return {@code List} items, {@code null} if an error has occurred.
     */
    private List readItems(String catalogName) {
        File file = new File(path + catalogName + "/items" + suffix);

        if (file.exists()) {
            try {
                return jsonUtil.asObject(file.toURI().toURL(), List.class);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    @Override
    public boolean deleteAll() throws PersistenceException {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
