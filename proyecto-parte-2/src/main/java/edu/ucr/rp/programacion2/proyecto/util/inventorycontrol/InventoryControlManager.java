package edu.ucr.rp.programacion2.proyecto.util.inventorycontrol;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogFileService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryFileService;
import edu.ucr.rp.programacion2.proyecto.logic.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to see make a relate one inventory with 1 o more catalogs.
 */
public class InventoryControlManager {
    //  Varaibles  \\
    private static InventoryControlManager instance;
    private List<InventoryControl> list;
    private InventoryFileService inventoryFileService;
    private CatalogFileService catalogFileService;

    //  Constructor  \\
    private InventoryControlManager() {
        inventoryFileService = InventoryFileService.getInstance();
        list = new ArrayList<>();
        updateList();
    }

    //  Singlenton Pattern  \\
    public static InventoryControlManager getInstance() {
        if (instance == null)
            instance = new InventoryControlManager();
        return instance;
    }
    //  Methods  \\

    /**
     * Return a List with the inventories an their catalogs.
     *
     * @return {@code List} list of names of catalogs linked with an Inventory.
     */
    public List<InventoryControl> getAll() {
        updateList();
        return list;
    }

    /**
     * This method search for each inventory, all the catalogs that are linked to it.
     * Create a new InventoryControl, for each catalog found in all the inventories.
     *
     * @return {@code List} List of inventories Controls
     */
    private void updateList() {
        list = new ArrayList<>();
        try {
            List<Inventory> inventories = inventoryFileService.getAll();
            // SEARCH IN EACH INVENTORY
            for (Inventory inventory : inventories) {
                List<Catalog> catalogs = getCatalogsOf(inventory);
                if (catalogs.isEmpty()) {
                    list.add(new InventoryControl(inventory.getName(), null));
                } else {
                    for (Catalog catalog : catalogs) {
                        list.add(new InventoryControl(inventory.getName(), catalog.getName()));
                    }
                }
            }
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param inventory
     * @return
     */
    private List<Catalog> getCatalogsOf(Inventory inventory) {
        catalogFileService = new CatalogFileService(inventory);
        try {
            return catalogFileService.getAll();
        } catch (ServiceException exception) {
            System.out.println(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public int size() {
        updateList();
        return list.size();
    }

}
