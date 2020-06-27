package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

import java.util.List;

public class InventoryRequest {
    // Variables
    private Inventory inventory;
    private List<Inventory> list;
    // Methods
    public Inventory getInventory() {

        return inventory;
    }

    public InventoryRequest setInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public List<Inventory> getList() {
        return list;
    }

    public InventoryRequest setList(List<Inventory> list) {
        this.list = list;
        return this;
    }
}
