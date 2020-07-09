package edu.ucr.rp.programacion2.proyecto.util.refresh;
import edu.ucr.rp.programacion2.proyecto.gui.modules.inventory.InventoryView;
import edu.ucr.rp.programacion2.proyecto.gui.modules.item.ManageItem;
import edu.ucr.rp.programacion2.proyecto.logic.InventorySocketService;
import edu.ucr.rp.programacion2.proyecto.util.ThreadPool;

import java.util.concurrent.RejectedExecutionException;

public class RefreshService {
    private static RefreshService instance;
    InventorySocketService inventorySocketService = InventorySocketService.getInstance();

    private RefreshService() {
    }

    public static RefreshService getInstance() {
        if (instance == null) {
            instance = new RefreshService();
        }
        return instance;
    }

    public static void refresh(RefreshConstants refreshConstants) throws RejectedExecutionException {
        ThreadPool.getPool().execute(() -> {
            while (true) {
                switch (refreshConstants) {
                    case INVENTORY_VIEW:
                        InventoryView.refresh();
                        break;
                    /*case DELETE_INVENTORY:
                        DeleteInventory.refresh();
                        break;
                    case CATALOG_FORM:
                        CreateCatalogForm.refresh();
                        break;
                    case CATALOG_CONFIG:
                        CatalogConfig.refresh();
                        break;
                    case DELETE_CATALOG:
                        DeleteCatalog.refresh();
                        break;*/
                    case MANAGE_ITEM:
                        ManageItem.refreshTable();
                        break;
                }
                ThreadPool.pause();
            }
        });

    }



}
