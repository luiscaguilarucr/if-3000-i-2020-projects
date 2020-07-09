package edu.ucr.rp.programacion2.proyecto.server;

import edu.ucr.rp.programacion2.proyecto.logic.CatalogFileService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryFileService;
import edu.ucr.rp.programacion2.proyecto.server.messages.Request;
import edu.ucr.rp.programacion2.proyecto.server.messages.RequestType;
import edu.ucr.rp.programacion2.proyecto.server.processes.*;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;
import edu.ucr.rp.programacion2.proyecto.util.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static edu.ucr.rp.programacion2.proyecto.server.messages.RequestType.CLOSE;
import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.receive;

public class Server {
    private ServerSocket serverSocket = null;
    private ProcessServerRequest processServerRequest;
    private ProcessCatalog catalogProcessRequest;
    private ProcessRequest inventoryProcessRequest;

    /**
     * Runs the server.
     *
     * @param port of the server.
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);              // Initialize the Server.
            initializeProcessRequests();                        // Initialize processRequests
            System.out.println("Esperando conexión");
            while (true) {
                Socket socket = serverSocket.accept();          // Wait for indeterminate client connections.
                ThreadPool.getPool().submit(() -> {
                    processRequest(socket);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();                           // Close Server connection.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processRequest(Socket socket) {
        try {
            Request request = receive(Request.class, socket); // Select the request type.
            System.out.println("Request received: " + request.getType());//

            // Classify the request
            RequestType type = request.getType();
            switch (type) {
                // Catalogs request
                case SET_INVENTORY:
                    catalogProcessRequest.refresh(socket);
                    break;
                case INSERT_CATALOG:
                    catalogProcessRequest.insert(socket);
                    break;
                case UPDATE_CATALOG:
                    catalogProcessRequest.update(socket);
                    break;
                case READ_CATALOG:
                    catalogProcessRequest.read(socket);
                    break;
                case READ_ALL_CATALOGS:
                    catalogProcessRequest.readAll(socket);
                    break;
                case DELETE_CATALOG:
                    catalogProcessRequest.delete(socket);
                    break;
                case DELETE_ALL_CATALOGS:
                    catalogProcessRequest.deleteAll(socket);
                    break;
                // Inventory request
                case INSERT_INVENTORY:
                    inventoryProcessRequest.insert(socket);
                    break;
                case UPDATE_INVENTORY:
                    inventoryProcessRequest.update(socket);
                    break;
                case READ_INVENTORY:
                    inventoryProcessRequest.read(socket);
                    break;
                case READ_ALL_INVENTORIES:
                    inventoryProcessRequest.readAll(socket);
                    break;
                case DELETE_INVENTORY:
                    inventoryProcessRequest.delete(socket);
                    break;
                case DELETE_ALL_INVENTORIES:
                    inventoryProcessRequest.deleteAll(socket);
                    break;
                case SERVER_STATUS:
                    processServerRequest.establishedConnection(socket);
                    break;
                // Close request
                default: {
                    request = receive(Request.class, socket);
                    if (request.getType().equals(CLOSE)) {
                        socket.close();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeProcessRequests() {
        catalogProcessRequest = new ProcessCatalogRequest(CatalogFileService.getInstance(), InventoryFileService.getInstance());
        inventoryProcessRequest = new ProcessInventoryRequest(InventoryFileService.getInstance());
        processServerRequest = new ProcessServerRequest();
    }
}