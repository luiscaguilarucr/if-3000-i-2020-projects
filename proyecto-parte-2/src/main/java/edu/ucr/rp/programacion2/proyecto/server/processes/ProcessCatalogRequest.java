package edu.ucr.rp.programacion2.proyecto.server.processes;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import edu.ucr.rp.programacion2.proyecto.logic.ServiceException;
import edu.ucr.rp.programacion2.proyecto.server.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.receive;
import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.send;

public class ProcessCatalogRequest implements ProcessCatalog {
    private CatalogService catalogService;
    private InventoryService inventoryService;

    public ProcessCatalogRequest(CatalogService catalogService, InventoryService inventoryService) {
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void insert(Socket socket) throws IOException, ClassNotFoundException {
        // Wait until receives an CatalogRequest.
        CatalogRequest catalogRequest = receive(CatalogRequest.class, socket);
        System.out.println("Recibiendo el catálogo: ");
        Catalog catalog = catalogRequest.getCatalog();
        System.out.println("Catálogo por agregar= " + catalog);
        // Add catalog.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (catalogService.add(catalog)) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("Catalog has been added");
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
        // Wait until receives an CatalogRequest.
        CatalogRequest catalogRequest = receive(CatalogRequest.class, socket);
        System.out.println("Recibiendo el catálogo: ");
        Catalog catalog = catalogRequest.getCatalog();
        System.out.println("Catálogo a editar= " + catalog);
        // Add catalog.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (catalogService.edit(catalog)) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("Catalog has been edited");
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
        // Wait until receives an CatalogReadRequest.
        CatalogReadRequest catalogReadRequest = receive(CatalogReadRequest.class, socket);
        System.out.println("Recibiendo el nombre del catálogo: ");
        String name = catalogReadRequest.getName();
        System.out.println("Nombre recibido= " + name);

        // Get the catalog.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        Catalog catalog = null;
        try {
            catalog = catalogService.get(name);
            confirmationRequest.setCompleted(true);
            confirmationRequest.setDetails("Catalog found.");

        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            confirmationRequest.setCompleted(false);
            confirmationRequest.setDetails(e.getMessage());
        }
        // Send confirmation.
        send(confirmationRequest, socket);

        // Send CatalogReadRequest.
        if (catalog != null) {
            CatalogRequest catalogRequest = new CatalogRequest();
            catalogRequest.setCatalog(catalog);
            send(catalogRequest, socket);
            System.out.println("Enviando el catálogo.");
        }
    }

    @Override
    public void readAll(Socket socket) throws IOException, ClassNotFoundException {
        CatalogListRequest catalogListRequest = new CatalogListRequest();
        try {
            // Get catalogs.
            List<Catalog> list = catalogService.getAll();
            // Add the list to the request
            catalogListRequest.setList(list);
        } catch (ServiceException serviceException) {
            System.out.println("Error in getAll() catalogs...");
        }
        // Sent List
        send(catalogListRequest, socket);
    }

    @Override
    public void delete(Socket socket) throws IOException, ClassNotFoundException {
        // Wait until receives an CatalogRequest.
        CatalogRequest catalogRequest = receive(CatalogRequest.class, socket);
        System.out.println("Recibiendo el catálogo: ");
        Catalog catalog = catalogRequest.getCatalog();
        System.out.println(catalog);
        // Remove catalog.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (catalogService.remove(catalog)) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("Catalog has been removed");
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
        // Remove all catalogs.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            if (catalogService.removeAll()) {
                confirmationRequest.setCompleted(true);
                confirmationRequest.setDetails("All the catalogs have been removed.");
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
    public void refresh(Socket socket) throws IOException, ClassNotFoundException {
        // Wait until receives an CatalogRequest.
        InventoryRequest inventoryRequest = receive(InventoryRequest.class, socket);
        System.out.println("Recibiendo el inventario: ");
        Inventory inventory = inventoryRequest.getInventory();
        System.out.println("Inventario por ejecutar= " + inventory);
        // Add inventory.
        ConfirmationRequest confirmationRequest = new ConfirmationRequest();
        try {
            catalogService.setInventory(inventory);
            confirmationRequest.setCompleted(true);
            confirmationRequest.setDetails("Inventory was put into action");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            confirmationRequest.setCompleted(false);
            confirmationRequest.setDetails(e.getMessage());
        }
        // Send confirmation.
        send(confirmationRequest, socket);
    }
}
