package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

import java.util.List;

public interface InventoryPersistance extends Persistence<Inventory, List<Inventory>>{
    boolean rename(String oldValue, String newValue);// TODO change this..
}
