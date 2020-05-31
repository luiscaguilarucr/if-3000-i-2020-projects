package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.io.Serializable;
import java.util.List;

public class Catalog implements Serializable {
    private int id;
    private String name;
    private List<Item> items;
    private List<String> schema; //TODO revisar schema.


    public Catalog(int id, String name, List<Item> items, List<String> schema) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.schema = schema;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        Catalog catalog = (Catalog) o;
        return id == catalog.id;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", schema=" + schema +
                '}';
    }
}
