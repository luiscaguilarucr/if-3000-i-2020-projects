package edu.ucr.rp.programacion2.proyecto.server.processes;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import edu.ucr.rp.programacion2.proyecto.logic.ServiceException;
import edu.ucr.rp.programacion2.proyecto.server.messages.ConfirmationRequest;
import edu.ucr.rp.programacion2.proyecto.server.messages.InventoryReadRequest;
import edu.ucr.rp.programacion2.proyecto.server.messages.InventoryListRequest;
import edu.ucr.rp.programacion2.proyecto.server.messages.InventoryRequest;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.receive;
import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.send;

public class ProcessInventoryRequest implements ProcessRequest {
    // Variables
    private InventoryService inventoryService;
    private JsonUtil jsonUtil = new JsonUtil();
    // Constructor


    public ProcessInventoryRequest(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void insert(Socket socket) throws IOException, ClassNotFoundException {
        // Wait until receives an InventoryRequest.
        InventoryRequest inventoryRequest = receive(InventoryRequest.class, socket);
        System.out.println("Recibiendo el inventario: ");
        Inventory inventory = inventoryRequest.getInventory();
        System.out.println("Inventario por agregar= " +  inventory);
        // Add inventory.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (inventoryService.add(inventory)) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("Inventory has been added");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            confirmationRequest.setCompleted(false);
            confirmationRequest.setDetails(e.getMessage());
        }
        // Send confirmation.
        send(confirmationRequest, socket);
    }

    @Override
    public void update(Socket socket) throws IOException, ClassNotFoundException {
        // Wait until receives an InventoryRequest.
        InventoryRequest inventoryRequest = receive(InventoryRequest.class, socket);
        System.out.println("Recibiendo el inventario: ");
        Inventory inventory = inventoryRequest.getInventory();
        System.out.println("Inventario a editar= " +  inventory);
        // Add inventory.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (inventoryService.edit(inventory)) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("Inventory has been edited");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            confirmationRequest.setCompleted(false);
            confirmationRequest.setDetails(e.getMessage());
        }
        // Send confirmation.
        send(confirmationRequest, socket);
    }

    @Override
    public void read(Socket socket) throws IOException, ClassNotFoundException {
        // Wait until receives an InventoryKeyRequest.
        InventoryReadRequest inventoryReadRequest = receive(InventoryReadRequest.class, socket);
        System.out.println("Recibiendo el nombre del inventario: ");
        String name = inventoryReadRequest.getName();
        System.out.println("Nombre recibido= " + name);

        // Get the inventory.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        Inventory inventory = null;
        try {
            inventory = inventoryService.get(name);
            confirmationRequest.setCompleted(true);
            confirmationRequest.setDetails("Inventory found.");

        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            confirmationRequest.setCompleted(false);
            confirmationRequest.setDetails(e.getMessage());
        }
        // Send confirmation.
        send(confirmationRequest, socket);

        // Send InventoryRequest.
        if (inventory != null) {
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(inventory);
            send(inventoryRequest, socket);
            System.out.println("Enviando el inventario.");
        }
    }

    @Override
    public void readAll(Socket socket) throws IOException, ClassNotFoundException {
        InventoryListRequest inventoryListRequest = new InventoryListRequest();
        try {
            // Get inventories.
            List<Inventory> list = inventoryService.getAll();
            // Add the list to the request
            inventoryListRequest.setList(list);
        } catch (ServiceException serviceException) {
            System.out.println("Error in getAll() inventories...");
        }
        // Sent List
        send(inventoryListRequest, socket);
    }

    @Override
    public void delete(Socket socket) throws IOException, ClassNotFoundException {
        // Wait until receives an InventoryRequest.
        InventoryRequest inventoryRequest = receive(InventoryRequest.class, socket);
        System.out.println("Recibiendo el inventario: ");
        Inventory inventory = inventoryRequest.getInventory();
        System.out.println(inventory);
        // Remove inventory.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (inventoryService.remove(inventory)) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("Inventory has been removed");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            confirmationRequest.setCompleted(false);
            confirmationRequest.setDetails(e.getMessage());
        }
        // Send confirmation.
        send(confirmationRequest, socket);
    }

    @Override
    public void deleteAll(Socket socket) throws IOException, ClassNotFoundException {
        // Remove all inventories.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (inventoryService.removeAll()) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("All the inventories have been removed.");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            confirmationRequest.setCompleted(false);
            confirmationRequest.setDetails(e.getMessage());
        }
        // Send confirmation.
        send(confirmationRequest, socket);
    }
}
