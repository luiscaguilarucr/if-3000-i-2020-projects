package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

import java.util.List;

/**
 * Catalog request
 *
 * Required: One valid Inventory.
 *
 * Usage: add, edit, delete one Catalog or get the list of catalogs that belong
 * to an specific inventory.
 *
 */
public class CatalogRequest {
    // Variables
    private Inventory inventory;
    private Catalog catalog;
    private List<Catalog> list;
    // Constructor
    public CatalogRequest(Inventory inventory) {
        this.inventory = inventory;
    }

    public CatalogRequest() {
    }
    // Methods
    public Inventory getInventory() {
        return inventory;
    }

    public CatalogRequest setInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public CatalogRequest setCatalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public List<Catalog> getList() {
        return list;
    }

    public CatalogRequest setList(List<Catalog> list) {
        this.list = list;
        return this;
    }
}
