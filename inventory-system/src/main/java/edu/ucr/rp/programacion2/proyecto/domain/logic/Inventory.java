package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;

public class Inventory {
    private String name;

    /**
     * Object Inventory that contains catalogs.
     * @param name name of the catalog.
     */
    public Inventory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
