package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

import java.util.List;

/**
 * This is a request that contains the list of catalogs.
 */
public class CatalogListRequest {
    // Variables
    private List<Catalog> list;
    // Methods
    public List<Catalog> getList() {
        return list;
    }

    public CatalogListRequest setList(List<Catalog> list) {
        this.list = list;
        return this;
    }
}
