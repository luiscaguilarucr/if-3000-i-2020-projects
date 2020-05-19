package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Carlos Aguilar Morales
 * @author Jeison David Araya Mena
 * This class handles the characteristics of an item
 * */

public class Item {
    private String name;
    private List<Feature> features;

    public Item(String name, List<Feature> features) {
        this.name = name;
        this.features = features;
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public Feature getFeature(String name) {
        //We traverse the ArrayList of features until we find a feature name that we're looking for
        for (int i = 0; i < features.size() - 1; i++) {
            if(features.get(i).getName().equalsIgnoreCase(name)){
                return features.get(i);
            }
        }
        return null;
    }

    public void removeFeature(Feature feature) {
        if (features.get(features.indexOf(feature)) != null) {
            features.remove(feature);
        }
    }
}
