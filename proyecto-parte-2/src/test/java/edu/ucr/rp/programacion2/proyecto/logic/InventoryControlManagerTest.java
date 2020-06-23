package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.util.inventorycontrol.InventoryControlManager;
import junit.framework.TestCase;

public class InventoryControlManagerTest extends TestCase {
    InventoryControlManager inventoryControlManager = InventoryControlManager.getInstance();

    public void testGetAll(){
        System.out.println(inventoryControlManager.getAll());
    }


}