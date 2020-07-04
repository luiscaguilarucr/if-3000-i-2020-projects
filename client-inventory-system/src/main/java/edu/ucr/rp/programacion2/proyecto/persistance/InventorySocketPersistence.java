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
            System.out.println("Se ha enviado una petición para agregar un inventario.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create an inventory request.
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(inventory);
            System.out.println("Se ha enviado el inventario." + inventory);

            // Send and wait the request.
            send(inventoryRequest, clientSocket);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("No fue agregado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            System.out.println("Se agregó correctamente");

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
            System.out.println("Se ha enviado una petición para editar un inventario.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create an inventory request.
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(inventory);
            System.out.println("Se ha enviado el inventario." + inventory);

            // Send and wait the request.
            send(inventoryRequest, clientSocket);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("No fue editado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            System.out.println("Se actualizó correctamente");

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
            System.out.println("Se ha enviado una petición para eliminar un inventario.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create an inventory request.
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(inventory);
            send(inventoryRequest, clientSocket);
            System.out.println("Se ha enviado el inventario." + inventory);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("No fue eliminado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            System.out.println("Se eliminó correctamente");
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
            System.out.println("Se ha enviado una petición para eliminar todos los inventarios.");
            // Send and wait the request.
            send(request, clientSocket);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("No fue eliminado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            System.out.println("Inventarios eliminados correctamente");
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
            System.out.println("Se ha enviado una petición para obtener un inventario.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create a read request.
            InventoryReadRequest inventoryReadRequest = new InventoryReadRequest();
            inventoryReadRequest.setName(key);
            send(inventoryReadRequest, clientSocket);
            System.out.println("Se ha enviado una petición para obtener un inventario con nombre " + key);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("No fue encontrado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            // Wait for an inventory
            System.out.println("El inventario existe, esperando para recibirlo.");
            InventoryRequest inventoryRequest = receive(InventoryRequest.class, clientSocket);
            Inventory inventory = inventoryRequest.getInventory();
            System.out.println("Inventario recibido= " + inventory);
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
            System.out.println("Se ha enviado una petición para obtener todos los inventarios.");
            // Send and wait the request.
            send(request, clientSocket);

            // Receives an InventoryListRequest.
            System.out.println("Esperando lista.");
            InventoryListRequest inventoryListRequest = receive(InventoryListRequest.class, clientSocket);
            List<Inventory> list = inventoryListRequest.getList();
            System.out.println("Lista recibida= " + list);

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
        Request closeRequest = new Request();
        closeRequest.setType(CLOSE);
        send(closeRequest, clientSocket);
        System.out.println("Se envió la petición para finalizar la conexión.");

        if (clientSocket != null) {
            clientSocket.close();
            System.out.println("Conexión cerrada");
        }
    }
}




