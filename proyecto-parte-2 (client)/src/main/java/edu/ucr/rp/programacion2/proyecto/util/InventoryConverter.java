package edu.ucr.rp.programacion2.proyecto.util;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.Map;

public class InventoryConverter extends StringConverter<Inventory> {
    private Map<String, Inventory> inventoryMap = new HashMap<>();

    @Override
    public String toString(Inventory inventory) {
        if(inventory == null){
            return null;
        }
        inventoryMap.put(inventory.getName(), inventory);
        return inventory.getName();
    }

    @Override
    public Inventory fromString(String inventoryName) {
        if (inventoryName == null) {
            return null;
        }
        return inventoryMap.get(inventoryName);
    }
}
