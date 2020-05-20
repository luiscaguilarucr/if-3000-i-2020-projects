package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;

public class FeatureService implements Service<Feature, String> {
    private List<Feature> features;

    @Override
    public boolean add(Feature object) {
        if(validateAddition(object)){
            features.add(object);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Feature object) {
        return features.remove(object);
    }

    @Override
    public Feature get(String name) {
        for (int i = 0; i < features.size(); i++) {
            if (features.get(i).getName().equalsIgnoreCase(name)) {
                return features.get(i);
            }
        }
        return null;
    }

    @Override
    public List getAll() {
        return features;
    }

    @Override
    public boolean modify(Feature object) {
        //tiene que existir
        //sea diferente de null
        if (object == null || !features.contains(object)) {
            return false;
        }
        features.add(features.indexOf(object), object);
        return true;
    }

    private boolean validateAddition(Feature object) {
        //para que se agregue no debe que existir otro igual
        //debe tener al menos una propiedad
        //validar que tenga nombre
        //que la propiedad sea igual al nombre del feature
        if (object == null || features.contains(object) || validateItemName(object)) {
            return false;
        } else if (object.getName() == null || object.getName().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateItemName(Feature object) {
        //TODO validar espacios vacíos
        return "".equals(object.getName());
    }
}
