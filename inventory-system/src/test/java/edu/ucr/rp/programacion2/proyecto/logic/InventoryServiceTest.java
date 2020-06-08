package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import junit.framework.TestCase;

import static edu.ucr.rp.programacion2.proyecto.util.Utility.randomInventory;
import static edu.ucr.rp.programacion2.proyecto.util.Utility.randomInventoryName;

public class InventoryServiceTest extends TestCase {
    InventoryService inventoryService = InventoryService.getInstance();

    public void testAdd() {
        for (int i = 0; i < 5 ; i++){
            Inventory inventory = randomInventory();
            if ( inventoryService.add(inventory))
                System.out.println("Se creó el inventario " + inventory);
            else
                System.out.println("No se creó el inventario " + inventory);
        }
    }

    public void testEdit() {
        Inventory inventory = inventoryService.get(randomInventoryName());
        if(inventory!=null){
            System.out.println("Old inventory: " + inventory);
            inventory.setName("newName");
            if(inventoryService.edit(inventory)){
                System.out.println("Se editó el inventorio " + inventory );
            }else {
                System.out.println("No se editó el inventorio " + inventory);
            }
        }
    }

    public void testRemove() {
        for (int i = 0; i < 0 ; i++){
            Inventory inventory = randomInventory();
            if ( inventoryService.remove(inventory))
                System.out.println("Se eliminó el inventario " + inventory);
            else
                System.out.println("No se eliminó el inventario " + inventory);
        }

        deleteAll();
    }

    public void testGet() {
    }

    public void testGetAll() {
        System.out.println(inventoryService.getAll());
    }

    public void deleteAll(){
        if ( inventoryService.removeAll())
            System.out.println("Se eliminaron los inventarios.");
        else
            System.out.println("No se eliminaron los inventarios.");
    }
}