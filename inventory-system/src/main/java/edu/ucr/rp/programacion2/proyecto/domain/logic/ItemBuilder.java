package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.ArrayList;
/**
 * @author Luis Carlos Aguilar Morales
 * @author Jeison David Araya Mena
 * This class by implementing a "Builder" layout pattern generates an object of type Item, gives it a name and a list of features
 * */
public class ItemBuilder {
    private String name;
    private ArrayList<Feature> features;

    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder withFeatures (ArrayList<Feature> features){
        this.features = features;
        return this;
    }

    public Item build(){
        return new Item(name,
                        features);
    }
}
