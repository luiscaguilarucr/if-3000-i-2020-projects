package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.messages.ConfirmationRequest;
import edu.ucr.rp.programacion2.proyecto.persistance.messages.InventoryRequest;
import edu.ucr.rp.programacion2.proyecto.persistance.messages.Request;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.persistance.messages.RequestType.CLOSE;
import static edu.ucr.rp.programacion2.proyecto.persistance.messages.RequestType.INSERT_INVENTORY;
import static edu.ucr.rp.programacion2.proyecto.persistance.processes.RequestProcessUtil.receive;
import static edu.ucr.rp.programacion2.proyecto.persistance.processes.RequestProcessUtil.send;


public class InventorySocketPersistence implements InventoryPersistance {
    //  Variables  \\
    private String host;
    private int port;
    private Socket clientSocket;
    private JsonUtil jsonUtil = new JsonUtil();
    //  Constructor \\

    public InventorySocketPersistence(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * @param inventory inventory to save.
     * @return {@code true} if the directory have been created or saved.{@code false} Otherwise.
     */
    @Override
    public boolean write(Inventory inventory) throws PersistenceException {

        try {
            return insert(inventory);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());// TODO
        }

    }

    private boolean insert(Inventory inventory) throws IOException, ClassNotFoundException, PersistenceException {
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
            System.out.println("Se envió la petición para finalizar la conexión.");


            return true;
        } finally {
            // Send close request
            Request closeRequest = new Request();
            closeRequest.setType(CLOSE);
            send(closeRequest, clientSocket);

            if (clientSocket != null)
                clientSocket.close();
            System.out.println("Conexión cerrada");
        }

    }


    public boolean rename(String oldValue, String newValue) {
        throw new UnsupportedOperationException("No implementado");
    }

    /**
     * Search and return a list with inventories.
     *
     * @return {@code List<Inventory>} List of the inventories.
     */
    @Override
    public List<Inventory> read() throws PersistenceException {
        return getInventories();
    }

    /**
     * Deletes an inventory.
     *
     * @param inventory to delete.
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public boolean delete(Inventory inventory) throws PersistenceException {

        return false;
    }

    /**
     * Deletes all the inventories.
     *
     * @return {@code true} if the directory have been removed or doesn't exists.{@code false} Otherwise.
     */
    @Override
    public boolean deleteAll() throws PersistenceException {
        return false;
    }

    /**
     * Search in the inventories path all the directories created.
     * Creates Inventory objects with the name of each subdirectory found.
     * Add the each inventory to a list.
     *
     * @return {@code List<Inventory>} List of the inventories found.
     */
    private List<Inventory> getInventories() {
        return null;
    }
}
