package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Catalog implements Serializable {

    private String name;
    private List<Item> items;
    private List<String> schema; //TODO revisar schema.

    /**
     * @param name       name of the catalogue
     * @param schema properties that are going to define the items.
     */
    public Catalog(String name, List<Item> items, List<String> schema) {//TODO valorar el uso de Item...items
        this.name = name;
        this.items = items;
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<String> getSchema() {
        return schema;
    }

    public void setSchema(List<String> schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory catalog = (Inventory) o;
        return Objects.equals(name, catalog.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", items=" + items +
                ", properties=" + schema +
                '}';
    }
}
