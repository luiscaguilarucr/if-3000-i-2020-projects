package edu.ucr.rp.programacion2.proyecto.util.builder;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Feature;

/**
 * @author Luis Carlos Aguilar Morales
 * @author Jeison David Araya Mena
 * This class by implementing a "Builder" design pattern generates an object of type Feature, gives it a name, a value, and a mandatory
 * */
public class FeatureBuilder {
    private String name;
    private Object value;
    private boolean mandatory;

    public FeatureBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FeatureBuilder withValue(Object value) {
        this.value = value;
        return this;
    }

    public FeatureBuilder withMandatory(boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public Feature build(){
        return new Feature(name,
                           value);
    }
}
