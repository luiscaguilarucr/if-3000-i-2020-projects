package edu.ucr.rp.programacion2.proyecto.domain;

import edu.ucr.rp.programacion2.proyecto.logic.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to see make a relate one inventory with 1 o more catalogs.
 */
public class InventoryControlManager {
    //  Varaibles  \\
    private static InventoryControlManager instance;
    private final InventoryService inventoryService;
    private CatalogService catalogService;

    //  Constructor  \\
    private InventoryControlManager() {
        inventoryService = InventorySocketService.getInstance();
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
        return getAll0();
    }

    /**
     * This method search for each inventory, all the catalogs that are linked to it.
     * Create a new InventoryControl, for each catalog found in all the inventories.
     *
     * @return {@code List} List of inventories Controls
     */
    private List<InventoryControl> getAll0() {
        List<InventoryControl> list = new ArrayList<>();
        try {
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
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * @param inventory
     * @return
     */
    private List<Catalog> getCatalogsOf(Inventory inventory) {
        catalogService = CatalogSocketService.getInstance();
        catalogService.setInventory(inventory);
        try {
            return catalogService.getAll();
        } catch (ServiceException exception) {
            System.out.println(exception.getMessage());
        }
        return new ArrayList<>();
    }


}
