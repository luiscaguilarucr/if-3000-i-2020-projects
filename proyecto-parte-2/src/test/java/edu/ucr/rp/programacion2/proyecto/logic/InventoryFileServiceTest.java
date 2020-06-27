package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import junit.framework.TestCase;

import static edu.ucr.rp.programacion2.proyecto.util.Utility.randomInventory;
import static edu.ucr.rp.programacion2.proyecto.util.Utility.randomInventoryName;

public class InventoryFileServiceTest extends TestCase {
    InventoryFileService inventoryFileService = InventoryFileService.getInstance();

    public void testAdd() {
        for (int i = 0; i < 2 ; i++){
            Inventory inventory = randomInventory();
            if ( inventoryFileService.add(inventory))
                System.out.println("Se creó el inventario " + inventory);
            else
                System.out.println("No se creó el inventario " + inventory);
        }
    }

    public void testEdit() {
        Inventory inventory = inventoryFileService.get(randomInventoryName());
        if(inventory!=null){
            System.out.println("Old inventory: " + inventory);
            inventory.setName("newName");
            if(inventoryFileService.edit(inventory)){
                System.out.println("Se editó el inventorio " + inventory );
            }else {
                System.out.println("No se editó el inventorio " + inventory);
            }
        }
    }

    public void testRemove() {
        for (int i = 0; i < 0 ; i++){
            Inventory inventory = randomInventory();
            if ( inventoryFileService.remove(inventory))
                System.out.println("Se eliminó el inventario " + inventory);
            else
                System.out.println("No se eliminó el inventario " + inventory);
        }

        deleteAll();
    }

    public void testGet() {
    }

    public void testGetAll() {
        System.out.println(inventoryFileService.getAll());
    }

    public void deleteAll(){
        if ( inventoryFileService.removeAll())
            System.out.println("Se eliminaron los inventarios.");
        else
            System.out.println("No se eliminaron los inventarios.");
    }
}