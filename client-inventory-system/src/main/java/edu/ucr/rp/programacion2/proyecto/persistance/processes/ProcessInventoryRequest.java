package edu.ucr.rp.programacion2.proyecto.persistance.processes;

import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.net.Socket;

public class ProcessInventoryRequest  implements ProcessRequest{
    // Variables
    private InventoryService inventoryService;
    private JsonUtil jsonUtil = new JsonUtil();
    // Constructor


    public ProcessInventoryRequest(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void insert(Socket socket) throws IOException, ClassNotFoundException {
//        InventoryRequest inventoryRequest = receive(InventoryRequest.class, socket);
//        System.out.println("Recibiendo el inventario: ");
//        Inventory inventory = inventoryRequest.getInventory();
//        System.out.println(inventory);
//        // Add inventory.
//        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
//        try {
//            if(inventoryService.add(inventory)){
//                confirmationRequest.setCompleted(true);
//                confirmationRequest.setDetails("Inventory has been added");
//            }
//        } catch (ServiceException e) {
//            System.out.println(e.getMessage());
//            confirmationRequest.setCompleted(false);
//            confirmationRequest.setDetails(e.getMessage());
//        }
//        // Send confirmation.
//        send(confirmationRequest, socket);
    }

    @Override
    public void update(Socket socket)  throws IOException, ClassNotFoundException{

    }

    @Override
    public void read(Socket socket) throws IOException, ClassNotFoundException {

    }

    @Override
    public void readAll(Socket socket) throws IOException, ClassNotFoundException{

    }

    @Override
    public void delete(Socket socket) throws IOException, ClassNotFoundException{

    }

    @Override
    public void deleteAll(Socket socket) throws IOException, ClassNotFoundException{

    }
}
