package edu.ucr.rp.programacion2.proyecto.server;

import edu.ucr.rp.programacion2.proyecto.logic.InventoryFileService;
import edu.ucr.rp.programacion2.proyecto.server.messages.Request;
import edu.ucr.rp.programacion2.proyecto.server.messages.RequestType;
import edu.ucr.rp.programacion2.proyecto.server.processes.ProcessCatalogRequest;
import edu.ucr.rp.programacion2.proyecto.server.processes.ProcessInventoryRequest;
import edu.ucr.rp.programacion2.proyecto.server.processes.ProcessRequest;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static edu.ucr.rp.programacion2.proyecto.server.messages.RequestType.CLOSE;
import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.receive;

public class Server {
    private JsonUtil jsonUtil = new JsonUtil();
    private ServerSocket serverSocket = null;
    private ProcessRequest catalogProcessRequest;
    private ProcessRequest inventoryProcessRequest;
    /**
     * Runs the server.
     * @param port of the server.
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);              // Initialize the Server.
            initializeProcessRequests();                        // Initialize processRequests
            System.out.println("Esperando conexión");
            while(true) {
                Socket socket = serverSocket.accept();          // Wait for indeterminate client connections.
                System.out.println("Conexión recibida");        // Connection with client established.

                Request request = receive(Request.class, socket); // Select the request type.
                System.out.println("Message Recibido: " + jsonUtil.asJson(request));//

                // Classify the request
                RequestType type = request.getType();
                switch (type){
                    // Catalogs request
                    case INSERT_CATALOG:    catalogProcessRequest.insert(socket); break;
                    case UPDATE_CATALOG:    catalogProcessRequest.update(socket); break;
                    case READ_CATALOG:      catalogProcessRequest.read(socket); break;
                    case READ_ALL_CATALOGS: catalogProcessRequest.readAll(socket); break;
                    case DELETE_CATALOG:    catalogProcessRequest.delete(socket); break;
                    case DELETE_ALL_CATALOGS: catalogProcessRequest.deleteAll(socket); break;
                    // Inventory request
                    case INSERT_INVENTORY: inventoryProcessRequest.insert(socket); break;
                    case UPDATE_INVENTORY: inventoryProcessRequest.update(socket); break;
                    case READ_INVENTORY: inventoryProcessRequest.read(socket); break;
                    case READ_ALL_INVENTORIES: inventoryProcessRequest.readAll(socket); break;
                    case DELETE_INVENTORY: inventoryProcessRequest.delete(socket); break;
                    case DELETE_ALL_INVENTORIES: inventoryProcessRequest.deleteAll(socket); break;
                    // Close request
                    default: {
                        request = receive(Request.class, socket);
                        if(request.getType().equals(CLOSE)) {
                            socket.close();
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();                           // Close Server connection.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void initializeProcessRequests(){
        catalogProcessRequest = new ProcessCatalogRequest();
        inventoryProcessRequest = new ProcessInventoryRequest(InventoryFileService.getInstance());
    }
}