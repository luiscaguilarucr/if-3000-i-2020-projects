package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryFilePersistence implements InventoryPersistence {
    //  Variables  \\
    private final String path = "files/inventories/";
    //  Constructor \\

    public InventoryFilePersistence() {
        verifyCatalogsDir(path);
    }

    //  Methods  \\
    private void verifyCatalogsDir(String path) {
        // Validate files/
        File root = new File("files");
        if (!root.exists()) {
            root.mkdir();
        }
        // Validate files/inventories/
        File file = new File(path);
        if (!file.exists())
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
    public synchronized boolean write(Inventory inventory) throws PersistenceException {
        if (inventory == null) throw new PersistenceException("The inventory is null.");
        List<Inventory> inventories = getInventories();
        // Check is exist.
        for(Inventory inventory1: inventories){
            if(inventory1.getId().equals(inventory.getId())){
                rename(inventory1.getDirectoryName(), inventory.getDirectoryName());
                return true;
            }
        }
        // Doesn't exists.
        File file = new File(path + inventory.getDirectoryName());;
        if (file.exists())
            throw new PersistenceException("The inventory file already exists.");
        else
            return file.mkdir();
    }


    private boolean rename(String oldValue, String newValue) throws PersistenceException {
        if (oldValue == null || oldValue.isEmpty()) throw new PersistenceException("Inventory can't be identify");
        if (newValue == null || newValue.isEmpty()) throw new PersistenceException("Inventory is not valid.");
        File oldFile = new File(path + oldValue);
        File newFile = new File(path + newValue);
        oldFile.renameTo(newFile);
        if (!oldFile.exists() && newFile.exists()) {
            return true;
        } else {
            throw new PersistenceException("Inventory hasn't been saved.");
        }
    }

    /**
     * Search and return a list with inventories.
     *
     * @return {@code List<Inventory>} List of the inventories.
     */
    @Override
    public List<Inventory> read() throws PersistenceException {
        return getInventories();
    }

    /**
     * Deletes an inventory.
     *
     * @param inventory to delete.
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public synchronized  boolean delete(Inventory inventory) throws PersistenceException {
        if (inventory == null) throw new PersistenceException("The inventory is null.");
        File file = new File(path + inventory.getDirectoryName());
        if (!file.exists()) return true;
        try {
            FileUtils.forceDelete(file);
            return true;
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Deletes all the inventories.
     *
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public boolean deleteAll() throws PersistenceException {
        File file = new File(path);
        if (!file.exists()) return true;
        try {
            FileUtils.cleanDirectory(file);
            return true;
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Search in the inventories path all the directories created.
     * Creates Inventory objects with the name of each subdirectory found.
     * Add the each inventory to a list.
     *
     * @return {@code List<Inventory>} List of the inventories found.
     */
    private List<Inventory> getInventories() throws PersistenceException {
        File file = new File(path);
        List<Inventory> inventories = new ArrayList();

        if (file.exists()) {
            String[] directories = file.list();
            if (directories != null)
                for (String directory : directories) {
                    Inventory inventory = new Inventory();
                    inventory.setInventory(directory);
                    inventories.add(inventory);
                }
            return inventories;
        } else {
            throw new PersistenceException("The inventory file doesn't exists.");
        }
    }
}
