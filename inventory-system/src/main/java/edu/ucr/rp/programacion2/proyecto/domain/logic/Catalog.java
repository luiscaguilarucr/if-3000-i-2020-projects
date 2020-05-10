package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;

public class Catalog {

    private String name;
    private List<Item> items;
    private List<Feature> properties;//TODO revisar properties.

    /**
     *
     * @param name name of the catalogue
     * @param properties properties that are going to define the items.
     */
    public Catalog(String name, List<Item> items, List<Feature> properties) {//TODO valorar el uso de Item...items
        this.name = name;
        this.items = items;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public List<Feature> getProperties() { //TODO valorar un retorno Feature[].
        return properties;
    }
}
