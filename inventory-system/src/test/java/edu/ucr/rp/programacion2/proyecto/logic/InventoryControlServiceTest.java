package edu.ucr.rp.programacion2.proyecto.logic;

import junit.framework.TestCase;

public class InventoryControlServiceTest extends TestCase {
    InventoryControlService inventoryControlService = InventoryControlService.getInstance();

    public void testGetAll(){
        System.out.println(inventoryControlService.getAll());
    }


}