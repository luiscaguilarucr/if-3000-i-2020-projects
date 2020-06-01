package edu.ucr.rp.programacion2.proyecto.util.builder;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Item;

import java.util.List;

public class CatalogBuilder {
    private Integer id;
    private String name;
    private List<Item> items;
    private List<String> schema;

    public CatalogBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public CatalogBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CatalogBuilder withItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public CatalogBuilder withSchema(List<String> schema) {
        this.schema = schema;
        return this;
    }

    public Catalog build(){
        return new Catalog(id, name,
                           items,
                           schema);
    }
}
