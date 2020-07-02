package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.Utility;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InventoryFilePersistenceTest extends TestCase {
    private InventoryFilePersistence inventoryFilePersistence;
    private List inventories;
    @Before
    public void initialize(){

        inventories = new ArrayList();
    }

    @Test
    public void testSave(){
        inventoryFilePersistence = new InventoryFilePersistence();
        for(int i = 0 ; i < 5; i++){
            Inventory inventory = Utility.randomInventory();
            if(inventoryFilePersistence.write(inventory))
                System.out.println("Se guardo: " + inventory);
            else
                System.out.println("- No se guardo: " + inventory);

        }
    }

    @Test
    public void testDelete(){
        for(int i = 0 ; i < 5; i++){
            Inventory inventory = Utility.randomInventory();
            if(inventoryFilePersistence.delete(inventory))
                System.out.println("Se eliminó: " + inventory);
            else
                System.out.println("- No se eliminó: " + inventory);

        }
    }

    @Test
    public void testGet(){
        inventoryFilePersistence = new InventoryFilePersistence();
        inventories = inventoryFilePersistence.read();
        System.out.println(inventories);
    }
    @Test
    public void testRemoveAll(){
        inventoryFilePersistence = new InventoryFilePersistence();
        System.out.println("deleteAll(): " + inventoryFilePersistence.deleteAll());
    }

}