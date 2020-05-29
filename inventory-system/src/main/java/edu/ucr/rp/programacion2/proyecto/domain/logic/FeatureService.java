package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;
import java.util.Map;

public class FeatureService {
    private Map<String, Object> features;
    private List schema;

    public FeatureService(List schema) {
        this.schema = schema;
    }

    public boolean add(String key, Object value) {
        if (validateAddition(key)) {
            features.put(key, value);
            return true;
        }
        return false;
    }

    public boolean remove(String key) {
        if (features.containsKey(key)) {
            features.remove(key);
            return true;
        }
        return false;
    }

    public boolean modify(String key, Object value) {
        if (features.containsKey(key)) {
            if (validateModify(key)){
                features.replace(key, value);
                return true;
            }
        }
        return false;
    }

    public Object get(String key) {
        if(features.containsKey(key)){
            return features.get(key);
        }
        return null;
    }

    public Map<String, Object> getAll() {
        return features;
    }

    private boolean validateAddition(String key) {
        //para que se agregue no debe que existir otro igual
        //debe tener al menos una propiedad
        //validar que tenga nombre
        //que la propiedad sea igual al nombre del feature
        if (features.containsKey(key) || validateItemName(key)) {
            return false;
        } else if (key == null) {
            return false;
        }
        return true;
    }
    private boolean validateModify(String key){
        if (validateItemName(key)) {
            return false;
        } else if (key == null) {
            return false;
        }
        return true;
    }
    private boolean validateItemName(String key) {
        //TODO validar espacios vacíos
        return "".equals(key);
    }
}
