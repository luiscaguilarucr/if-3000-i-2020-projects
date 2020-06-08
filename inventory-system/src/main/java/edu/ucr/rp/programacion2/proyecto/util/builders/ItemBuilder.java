package edu.ucr.rp.programacion2.proyecto.util.builders;

import edu.ucr.rp.programacion2.proyecto.domain.Item;

import java.util.Map;

/**
 * @author Luis Carlos Aguilar Morales
 * @author Jeison David Araya Mena
 * This class by implementing a "Builder" layout pattern generates an object of type Item, gives it a name and a list of features
 * */
public class ItemBuilder {
    //  Variables  \\
    private Map<String, Object> features;

    //  Setters  \\

    public ItemBuilder setFeatures(Map<String, Object> features) {
        this.features = features;
        return this;
    }


    //  Builder  \\
    public Item build(){
        return new Item(features);
    }
}
