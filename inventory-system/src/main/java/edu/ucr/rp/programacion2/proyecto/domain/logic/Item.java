package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.Map;

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
}
