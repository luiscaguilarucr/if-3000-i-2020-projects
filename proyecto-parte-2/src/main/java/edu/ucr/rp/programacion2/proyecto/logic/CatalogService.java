package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

public interface CatalogService extends Service<Catalog, String>{
    CatalogFileService setInventory(Inventory inventory);
    Inventory getInventory();
}
