package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.persistance.messages.RequestType.*;
import static edu.ucr.rp.programacion2.proyecto.util.RequestProcessUtil.receive;
import static edu.ucr.rp.programacion2.proyecto.util.RequestProcessUtil.send;

/**
 * This class sends a receives data to a server.
 */
public class InventorySocketPersistence implements InventoryPersistence {
    //  Variables  \\
    private final String host;
    private final int port;
    private Socket clientSocket;
    //  Constructor \\

    /**
     * Information used to establish the connection to the server.
     *
     * @param host server ip.
     * @param port server port.
     */
    public InventorySocketPersistence(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // Methods \\

    /**
     * Send an insert request to the server and then receives a confirmation.
     *
     * @param inventory inventory to insert.
     * @return {@code true} if the directory have been saved.{@code false} Otherwise.
     * @throws PersistenceException if the inventory is not valid or the connection has failed.
     */
    @Override
    public boolean insert(Inventory inventory) throws PersistenceException {
        try {
            return insert0(inventory);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }

    }

    /**
     * Send an update request to the server and then receives a confirmation.
     *
     * @param inventory to be updated.
     * @return {@code true} if the directory have been saved.{@code false} Otherwise.
     * @throws PersistenceException if the inventory is not valid or the connection has failed.
     */
    @Override
    public boolean update(Inventory inventory) throws PersistenceException {
        try {
            return update0(inventory);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Send an update request to the server using the name to identify the inventory. Then receives the inventory if exists.
     *
     * @param name of the inventory.
     * @return {Inventory} inventory has been found.
     * @throws PersistenceException if the inventory is not found or the connection has failed.
     */
    @Override
    public Inventory read(String name) throws PersistenceException {
        try {
            return read0(name);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Search and return a list with inventories.
     *
     * @return {@code List<Inventory>} List of the inventories.
     * @throws PersistenceException if the connection has failed.
     */
    @Override
    public List<Inventory> readAll() throws PersistenceException {
        try {
            return readAll0();

        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }

    }

    /**
     * Deletes an inventory.
     *
     * @param inventory to delete.
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public boolean delete(Inventory inventory) throws PersistenceException {
        try {
            return delete0(inventory);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Deletes all the inventories.
     *
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public boolean deleteAll() throws PersistenceException {
        try {
            return deleteAll0();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    private boolean insert0(Inventory inventory) throws IOException, ClassNotFoundException, PersistenceException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a insert request.
            request.setType(INSERT_INVENTORY);
            // Send and wait the request.
            send(request, clientSocket);

            // Create an inventory request.
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(inventory);

            // Send and wait the request.
            send(inventoryRequest, clientSocket);

            // Receives a ConfirmationRequest.
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                throw new PersistenceException(confirmationRequest.getDetails());
            }

            return true;
        } finally {
            closeConnection();
        }

    }

    private boolean update0(Inventory inventory) throws IOException, ClassNotFoundException, PersistenceException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a update request.
            request.setType(UPDATE_INVENTORY);

            // Send and wait the request.
            send(request, clientSocket);

            // Create an inventory request.
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(inventory);

            // Send and wait the request.
            send(inventoryRequest, clientSocket);

            // Receives a ConfirmationRequest.
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            return true;
        } finally {
            closeConnection();
        }

    }

    private boolean delete0(Inventory inventory) throws IOException, ClassNotFoundException, PersistenceException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a delete request.
            request.setType(DELETE_INVENTORY);
            // Send and wait the request.
            send(request, clientSocket);

            // Create an inventory request.
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(inventory);
            send(inventoryRequest, clientSocket);

            // Receives a ConfirmationRequest.
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            return true;
        } finally {
            closeConnection();
        }
    }

    private boolean deleteAll0() throws IOException, ClassNotFoundException, PersistenceException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a read all request.
            request.setType(DELETE_ALL_INVENTORIES);
            // Send and wait the request.
            send(request, clientSocket);

            // Receives a ConfirmationRequest.
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            return true;
        } finally {
            closeConnection();
        }
    }

    private Inventory read0(String key) throws IOException, PersistenceException, ClassNotFoundException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a read all request.
            request.setType(READ_INVENTORY);
            // Send and wait the request.
            send(request, clientSocket);

            // Create a read request.
            InventoryReadRequest inventoryReadRequest = new InventoryReadRequest();
            inventoryReadRequest.setName(key);
            send(inventoryReadRequest, clientSocket);

            // Receives a ConfirmationRequest.
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            // Wait for an inventory
            InventoryRequest inventoryRequest = receive(InventoryRequest.class, clientSocket);
            Inventory inventory = inventoryRequest.getInventory();
            return inventory;
        } finally {
            closeConnection();
        }
    }


    private List<Inventory> readAll0() throws IOException, ClassNotFoundException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a read all request.
            request.setType(READ_ALL_INVENTORIES);
            // Send and wait the request.
            send(request, clientSocket);

            // Receives an InventoryListRequest.
            InventoryListRequest inventoryListRequest = receive(InventoryListRequest.class, clientSocket);
            List<Inventory> list = inventoryListRequest.getList();

            return list;
        } finally {
            closeConnection();
        }
    }

    /**
     * Send a close request to the server and close the client socket connection.
     *
     * @throws IOException when the connection failed.
     */
    private void closeConnection() throws IOException {
        if (clientSocket != null) {
            Request closeRequest = new Request();
            closeRequest.setType(CLOSE);
            send(closeRequest, clientSocket);
            clientSocket.close();
        }

    }
}




