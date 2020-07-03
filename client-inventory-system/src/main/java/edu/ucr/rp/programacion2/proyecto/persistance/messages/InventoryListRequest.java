package edu.ucr.rp.programacion2.proyecto.persistance.messages;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

import java.util.List;

/**
 * This is a request that contains the list of inventories.
 */
public class InventoryListRequest {
    // Variables
    private List<Inventory> list;
    // Methods
    public List<Inventory> getList() {
        return list;
    }

    public InventoryListRequest setList(List<Inventory> list) {
        this.list = list;
        return this;
    }
}
