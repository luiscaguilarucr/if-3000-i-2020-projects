package edu.ucr.rp.programacion2.proyecto.domain;

import java.util.Objects;
import java.util.UUID;

public class Inventory {
    // Variables
    private UUID id;
    private String name;
    private String directoryName;
    // Constructor
    /**
     * Object Inventory that contains catalogs.
     * @param name name of the catalog.
     */
    public Inventory(String name) {
        this.name = name;
    }

    public Inventory() {  }
    // Methods

    public UUID getId() {
        return id;
    }

    public Inventory setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDirectoryName(){
        directoryName = name+"."+id;
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public void setInventory(String directoryName){
        String[]values=directoryName.split("\\.");
        name=values[0];
        id=UUID.fromString(values[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", directoryName='" + directoryName + '\'' +
                '}';
    }


}
