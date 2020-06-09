package edu.ucr.rp.programacion2.proyecto.util.inventorycontrol;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to see make a relate one inventory with 1 o more catalogs.
 */
public class InventoryControlService {//TODO change name(don't use Service)
    //  Varaibles  \\
    private static InventoryControlService instance;
    private List<InventoryControl> list;
    private InventoryService inventoryService;
    private CatalogService catalogService;

    //  Constructor  \\
    private InventoryControlService() {
        inventoryService = InventoryService.getInstance();
        list = new ArrayList<>();
        updateList();
    }

    //  Singlenton Pattern  \\
    public static InventoryControlService getInstance() {
        if (instance == null)
            instance = new InventoryControlService();
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
        List<Inventory> inventories = inventoryService.getAll();
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
    }

    /**
     * @param inventory
     * @return
     */
    private List<Catalog> getCatalogsOf(Inventory inventory) {
        catalogService = new CatalogService(inventory);
        return catalogService.getAll();
    }

    public int size() {
        updateList();
        return list.size();
    }

}
