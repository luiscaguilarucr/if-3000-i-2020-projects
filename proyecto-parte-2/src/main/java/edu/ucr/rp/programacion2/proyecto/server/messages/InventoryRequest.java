package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

public class InventoryRequest {
    // Variable
    private Inventory inventory;
    // Methods


    public Inventory getInventory() {
        return inventory;
    }

    public InventoryRequest setInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }
}
