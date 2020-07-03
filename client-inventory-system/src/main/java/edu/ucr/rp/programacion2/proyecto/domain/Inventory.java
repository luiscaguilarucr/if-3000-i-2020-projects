package edu.ucr.rp.programacion2.proyecto.domain;

import java.util.Objects;

public class Inventory {
    // Variables
    private String name;
    // Constructor

    public Inventory() {
    }

    // Methods
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(name, inventory.name);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "name='" + name + '\'' +
                '}';
    }
}
