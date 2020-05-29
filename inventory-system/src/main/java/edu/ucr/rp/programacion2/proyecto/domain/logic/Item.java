package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Item {
    private String name;
    private Map<String, Object> features;

    /**
     * Object Item that contains features.
     * @param name item name
     * @param features
     */
    public Item(String name, Map<String, Object> features) {
        this.name = name;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Object> features) {
        this.features = features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) &&
                Objects.equals(features, item.features);
    }

}
