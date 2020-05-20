package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    private String name;
    private List<Feature> features;

    /**
     * Object Item that contains features.
     * @param name item name
     * @param features
     */
    public Item(String name, List<Feature> features) {
        this.name = name;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

}
