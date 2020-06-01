package edu.ucr.rp.programacion2.proyecto.business_rules.io;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.Utility;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InventoryPersistenceTest extends TestCase {
    private InventoryPersistence inventoryPersistence;
    private List inventories;
    @Before
    public void initialize(){

        inventories = new ArrayList();
    }

    @Test
    public void testSave(){
        inventoryPersistence = new InventoryPersistence();
        for(int i = 0 ; i < 5; i++){
            Inventory inventory = Utility.randomInventory();
            if(inventoryPersistence.write(inventory))
                System.out.println("Se guardo: " + inventory);
            else
                System.out.println("- No se guardo: " + inventory);

        }
    }

    @Test
    public void testDelete(){
        for(int i = 0 ; i < 5; i++){
            Inventory inventory = Utility.randomInventory();
            if(inventoryPersistence.delete(inventory))
                System.out.println("Se eliminó: " + inventory);
            else
                System.out.println("- No se eliminó: " + inventory);

        }
    }

    @Test
    public void testGet(){
        inventoryPersistence = new InventoryPersistence();
        inventories = inventoryPersistence.read();
        System.out.println(inventories);
    }
    @Test
    public void testRemoveAll(){
        inventoryPersistence = new InventoryPersistence();
        System.out.println("deleteAll(): " + inventoryPersistence.deleteAll());
    }

}