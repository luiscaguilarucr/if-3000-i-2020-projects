package edu.ucr.rp.programacion2.proyecto.domain;

import java.util.Map;
import java.util.Objects;

public class Item {
    private String name;
    private Map<String, Object> features;

    /**
     * Object Item that contains features.
     *
     * @param name item name
     * @param features map of features.
     */
    public Item(String name, Map<String, Object> features) {
        this.name = name;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, Object> getFeatures() {
        return features;
    }

    public Item setFeatures(Map<String, Object> features) {
        this.features = features;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", features=" + features +
                '}';
    }
}
