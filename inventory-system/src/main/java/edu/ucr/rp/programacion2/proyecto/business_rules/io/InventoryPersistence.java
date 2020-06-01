package edu.ucr.rp.programacion2.proyecto.business_rules.io;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class InventoryPersistence implements Persistence<Inventory, String, List> {
    //  Variables  \\
    //private String path = System.getProperty("s");
    private final String path = "files/inventories/";
    //  Constructor \\

    public InventoryPersistence() {
    }

    //  Methods  \\

    /**
     * Creates a new directory with the name of the inventory.
     * Checks if the directory exists.
     *
     * @param inventory inventory to save.
     * @return {@code true} if the directory have been created or saved.{@code false} Otherwise.
     */
    @Override
    public boolean save(Inventory inventory) {
        if (inventory == null) return false;
        File file = new File(path + inventory.getName());
        if (file.exists())
            return true;
        else
            return file.mkdir();
    }

    /**
     * Search and return a list with inventories.
     *
     * @return {@code List<Inventory>} List of the inventories.
     */
    @Override
    public List get() {
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
        return file.delete();//TODO deletesubdirectories.
    }

    /**
     * Deletes all the inventories.
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    public boolean deleteAll(){
        File file = new File(path);
        if (!file.exists()) return true;
        return file.delete();//TODO clean directory
    }
    /**
     * Search in the inventories path all the directories created.
     * Creates Inventory objects with the name of each subdirectory found.
     * Add the each inventory to a list.
     *
     * @return {@code List<Inventory>} List of the inventories found.
     */
    private List getInventories() {
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
