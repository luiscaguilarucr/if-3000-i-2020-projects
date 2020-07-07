package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.messages.*;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.persistance.messages.RequestType.*;
import static edu.ucr.rp.programacion2.proyecto.util.RequestProcessUtil.receive;
import static edu.ucr.rp.programacion2.proyecto.util.RequestProcessUtil.send;

public class CatalogSocketPersistence implements CatalogPersistence {

    private String host;
    private int port;
    private Socket clientSocket;
    private JsonUtil jsonUtil = new JsonUtil();
    private Inventory inventory;

    public CatalogSocketPersistence(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean insert(Catalog catalog) throws PersistenceException {
        try {
            refresh();
            return insertRequest(catalog);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());// TODO
        }
    }

    @Override
    public boolean update(Catalog catalog) throws PersistenceException {
        try {
            refresh();
            return updateRequest(catalog);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public Catalog read(String key) throws PersistenceException {
        try {
            refresh();
            return readRequest(key);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Catalog> readAll() throws PersistenceException {
        try {
            refresh();
            return readAllRequest();

        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Catalog catalog) throws PersistenceException {
        try {
            refresh();
            return deleteRequest(catalog);
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public boolean deleteAll() throws PersistenceException {
        try {
            refresh();
            return deleteAllRequest();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    private boolean insertRequest(Catalog catalog) throws IOException, ClassNotFoundException, PersistenceException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a insert request.
            request.setType(INSERT_CATALOG);
            System.out.println("Se ha enviado una petición para agregar un catálogo.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create an catalog request.
            CatalogRequest catalogRequest = new CatalogRequest();
            catalogRequest.setCatalog(catalog);
            System.out.println("Se ha enviado el catálogo." + catalog);

            // Send and wait the request.
            send(catalogRequest, clientSocket);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("El catálogo no fue agregado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            System.out.println("Se agregó correctamente");

            return true;
        } finally {
            // Send close request
            closeConnection();
        }
    }

    private boolean updateRequest(Catalog catalog) throws IOException, ClassNotFoundException, PersistenceException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a update request.
            request.setType(UPDATE_CATALOG);
            System.out.println("Se ha enviado una petición para editar un catálogo.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create an catalog request.
            CatalogRequest catalogRequest = new CatalogRequest();
            catalogRequest.setCatalog(catalog);

            System.out.println("Se ha enviado el catálogo." + catalog);

            // Send and wait the request.
            send(catalogRequest, clientSocket);

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
            // Send close request
            closeConnection();
        }
    }

    private Catalog readRequest(String key) throws IOException, PersistenceException, ClassNotFoundException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a read all request.
            request.setType(READ_CATALOG);
            System.out.println("Se ha enviado una petición para obtener un catálogo.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create a read request.
            CatalogReadRequest catalogReadRequest = new CatalogReadRequest();
            catalogReadRequest.setName(key);
            send(catalogReadRequest, clientSocket);
            System.out.println("Se ha enviado una petición para obtener un catálogo con nombre " + key);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("No fue encontrado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            // Wait for an catalog
            System.out.println("El catálogo existe, esperando para recibirlo.");
            CatalogRequest catalogRequest = receive(CatalogRequest.class, clientSocket);
            Catalog catalog = catalogRequest.getCatalog();
            System.out.println("Catálogo recibido= " + catalog);
            return catalog;
        } finally {
            // Send close request
            closeConnection();
        }
    }

    private List<Catalog> readAllRequest() throws IOException, ClassNotFoundException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a read all request.
            request.setType(READ_ALL_CATALOGS);
            System.out.println("Se ha enviado una petición para obtener todos los catálogos.");
            // Send and wait the request.
            send(request, clientSocket);

            // Receives an CatalogListRequest.
            System.out.println("Esperando lista.");
            CatalogListRequest catalogListRequest = receive(CatalogListRequest.class, clientSocket);
            List<Catalog> list = catalogListRequest.getList();
            System.out.println("Lista recibida= " + list);

            return list;
        } finally {
            // Send close request
            closeConnection();
        }
    }

    private boolean deleteRequest(Catalog catalog) throws IOException, ClassNotFoundException, PersistenceException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a delete request.
            request.setType(DELETE_CATALOG);
            System.out.println("Se ha enviado una petición para eliminar un catálogo.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create an catalog request.
            CatalogRequest catalogRequest = new CatalogRequest();
            catalogRequest.setCatalog(catalog);
            send(catalogRequest, clientSocket);
            System.out.println("Se ha enviado el catálogo." + catalog);

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
            // Send close request
            closeConnection();
        }
    }

    private boolean deleteAllRequest() throws IOException, ClassNotFoundException, PersistenceException {
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
            // Send close request
            closeConnection();
        }
    }

    private boolean refresh() throws IOException, ClassNotFoundException, PersistenceException{
        Request request = new Request();
        try {
            // Establish the connection with the server.
            clientSocket = new Socket(host, port);
            // Create a insert request.
            request.setType(SET_INVENTORY);
            System.out.println("Se ha enviado una petición para posicionar el inventario.");
            // Send and wait the request.
            send(request, clientSocket);

            // Create an catalog request.
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setInventory(getInventory());
            System.out.println("Se ha enviado el inventario" + inventory.getName());

            // Send and wait the request.
            send(inventoryRequest, clientSocket);

            // Receives a ConfirmationRequest.
            System.out.println("Esperando confirmación.");
            ConfirmationRequest confirmationRequest = receive(ConfirmationRequest.class, clientSocket);

            if (!confirmationRequest.isCompleted()) {
                System.out.println("No fue posicionado");
                throw new PersistenceException(confirmationRequest.getDetails());
            }
            System.out.println("Se posicionó correctamente");

            return true;
        } finally {
            // Send close request
            closeConnection();
        }
    }

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
