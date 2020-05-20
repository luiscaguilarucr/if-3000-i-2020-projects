package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;

public class ItemService implements Service<Item, String> {
    private List<Item> itemList;
    private List<Feature> schema;

    public ItemService(List<Feature> schema) {
        this.schema = schema;
    }

    @Override
    public boolean add(Item object) {
        if (validateAddition(object) && validateSchema(object)) {
            itemList.add(object); //agrega el item a la lista
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Item object) {
        return itemList.remove(object);
    }

    @Override
    public Item get(String name) {
        //tiene que existir
        Item temp = new Item(name, null);
        int index = itemList.indexOf(temp);
        if (index == -1) {
            return null;
        }
        return itemList.get(index);
    }

    @Override
    public List getAll() {
        return itemList;
    }

    @Override
    public boolean modify(Item object) {
        //tiene que existir
        //sea diferente de null
        if (object == null || !itemList.contains(object)) {
            return false;
        }
        itemList.add(itemList.indexOf(object), object);
        return true;
    }

    private boolean validateAddition(Item object) {
        //para que se agregue no debe que existir otro igual
        //debe tener al menos una propiedad
        //validar que tenga nombre
        //que la propiedad sea igual al nombre del feature
        if (object == null || itemList.contains(object) || validateItemName(object)) {
            return false;
        } else if (object.getFeatures() == null || object.getFeatures().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateSchema(Item object) {
        if (schema.size() != object.getFeatures().size()) {
            return false;
        }
        return validateFeatures(object.getFeatures());
    }

    private boolean validateFeatures(List<Feature> features){
        int x = 0;
        while (x < schema.size()) {
            if (!features.contains(schema.get(x))) { //valida si un feature no existe
                return false;
            }
            x++;
        }
        return true;
    }

    private boolean validateItemName(Item object) {
        //TODO validar espacios vacíos
        return "".equals(object.getName());
    }

}
