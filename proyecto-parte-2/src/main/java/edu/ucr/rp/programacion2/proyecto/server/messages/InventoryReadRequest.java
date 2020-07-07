package edu.ucr.rp.programacion2.proyecto.server.messages;

public class InventoryReadRequest {
    // Variables
    String name;
    // Methods

    public String getName() {
        return name;
    }

    public InventoryReadRequest setName(String name) {
        this.name = name;
        return this;
    }
}
