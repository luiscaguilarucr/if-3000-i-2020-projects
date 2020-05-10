package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.ArrayList;

public class Catalog {

    private String name;
    private Item items;
    private List<Items> properties;

    /**
     *
     * @param name name of the catalogue
     * @param properties properties that are going to define the items.
     */
    public Catalog(String name, List<Items> items, ArrayList<Feature> properties) {//TODO valorar el uso de Item...items
        this.name = name;
        this.items = items;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public List<Items> getProperties() { //TODO valorar un retorno Feature[].
        return properties;
    }
}
