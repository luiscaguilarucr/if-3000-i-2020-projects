package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;

import java.util.List;

public class InventoryService implements Service<Inventory, String, List>{

    @Override
    public boolean add(Inventory element) {
        return false;
    }

    @Override
    public boolean edit(Inventory element) {
        return false;
    }

    @Override
    public boolean remove(Inventory element) {
        return false;
    }

    @Override
    public Inventory get(String key) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }
}
