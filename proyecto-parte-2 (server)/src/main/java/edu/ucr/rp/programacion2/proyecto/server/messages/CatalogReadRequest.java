package edu.ucr.rp.programacion2.proyecto.server.messages;

public class CatalogReadRequest {
    // Variables
    String name;
    // Methods

    public String getName() {
        return name;
    }

    public CatalogReadRequest setName(String name) {
        this.name = name;
        return this;
    }
}
