package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class InventoryFilePersistence implements InventoryPersistance {
    //  Variables  \\
    private final String path = "files/inventories/";
    //  Constructor \\

    public InventoryFilePersistence() {
        verifyCatalogsDir(path);
    }

    //  Methods  \\
    private void verifyCatalogsDir(String path){
        // Validate files/
        File root = new File("files");
        if(!root.exists()) {
            root.mkdir();
        }
        // Validate files/inventories/
        File file = new File(path);
        if(!file.exists())
            file.mkdir();
    }
    /**
     * Creates a new directory with the name of the inventory.
     * Checks if the directory exists.
     *
     * @param inventory inventory to save.
     * @return {@code true} if the directory have been created or saved.{@code false} Otherwise.
     */
    @Override
    public boolean write(Inventory inventory) {
        if (inventory == null) return false;
        File file = new File(path + inventory.getName());
        if (file.exists())
            return false;
        else
            return file.mkdir();
    }


    public boolean rename(String oldValue, String newValue){
        if (oldValue == null || oldValue.isEmpty()) return false;
        if (newValue == null || newValue.isEmpty()) return false;
        File oldFile = new File(path + oldValue);
        File newFile = new File(path + newValue);
        oldFile.renameTo(newFile);
        return !oldFile.exists() && newFile.exists();
    }
    /**
     * Search and return a list with inventories.
     *
     * @return {@code List<Inventory>} List of the inventories.
     */
    @Override
    public List<Inventory> read() {
        return getInventories();
    }

    /**
     * Deletes an inventory.
     *
     * @param inventory to delete.
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public boolean delete(Inventory inventory) {
        if (inventory == null) return false;
        File file = new File(path + inventory.getName());
        if (!file.exists()) return true;
        try {
            FileUtils.forceDelete(file);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Deletes all the inventories.
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public boolean deleteAll(){
        File file = new File(path);
        if (!file.exists()) return true;
        try {
            FileUtils.cleanDirectory(file);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     * Search in the inventories path all the directories created.
     * Creates Inventory objects with the name of each subdirectory found.
     * Add the each inventory to a list.
     *
     * @return {@code List<Inventory>} List of the inventories found.
     */
    private List<Inventory> getInventories() {
        File file = new File(path);
        List<Inventory> inventories = new ArrayList();

        if (file.exists()) {
            String[] names = file.list();
            if (names != null)
                for (String name : names) {
                    inventories.add(new Inventory(name));
                }
        }
        return inventories;
    }
}
