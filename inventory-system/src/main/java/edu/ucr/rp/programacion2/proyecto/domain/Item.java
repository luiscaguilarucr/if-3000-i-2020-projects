package edu.ucr.rp.programacion2.proyecto.domain;

import java.util.Map;
import java.util.Objects;

public class Item {
    private Map<String, Object> features;

    /**
     * Object Item that contains features.
     *
     * @param features map of features.
     */
    public Item(Map<String, Object> features) {
        this.features = features;
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
        return Objects.equals(features, item.features);
    }
    
    @Override
    public String toString() {
        return "Item{" +
                "features=" + features +
                '}';
    }
}
