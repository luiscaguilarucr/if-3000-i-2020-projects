package edu.ucr.rp.programacion2.proyecto.logic;


class InventorySocketServiceTest {

    public static void main(String[] args) {
        InventoryService inventoryService = InventorySocketService.getInstance();

        try {
            System.out.println(inventoryService.get("Carros"));
            System.out.println(inventoryService.get("Aviones"));
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}