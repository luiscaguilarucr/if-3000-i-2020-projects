package edu.ucr.rp.programacion2.proyecto.domain;

import edu.ucr.rp.programacion2.proyecto.util.inventorycontrol.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Catalog{
    //  Variable  \\
    private Configuration configuration;
    private String name;
    private List<Map> items;
    private List<String> schema;
    //  Constructor  \\
    public Catalog(Configuration configuration, String name, List<Map> items, List<String> schema) {
        this.configuration = configuration;
        this.name = name;
        this.items = items;
        this.schema = schema;
    }

    //  Methods  \\
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map> getItems() {
        return items;
    }

    public void setItems(List<Map> items) {
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
        return Objects.equals(configuration, catalog.configuration);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "configuration=" + configuration +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", schema=" + schema +
                '}';
    }
}
