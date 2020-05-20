package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Add a new feature.
     * @param feature
     */
    public void addFeature(Feature feature) {
        features.add(feature);
    }

    /**
     * Get a feature by name.
     * @param name feature name
     * @return
     */
    public Feature getFeature(String name) {
        //Traverse the ArrayList of features until we find a feature name that we're looking for
        for (int i = 0; i < features.size() - 1; i++) {
            if(features.get(i).getName().equalsIgnoreCase(name)){
                return features.get(i);
            }
        }
        return null;
    }

    /**
     * Removes a feature.
     * @param feature
     */
    public void removeFeature(Feature feature) {
        if (features.get(features.indexOf(feature)) != null) {
            features.remove(feature);
        }
    }
}
