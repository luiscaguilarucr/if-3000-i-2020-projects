package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.InventoryPersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.InventorySocketPersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.PersistenceException;
import edu.ucr.rp.programacion2.proyecto.logic.SocketConstants;

import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.logic.SocketConstants.HOST;
import static edu.ucr.rp.programacion2.proyecto.logic.SocketConstants.PORT;


public class InventorySocketService implements InventoryService {
    //  Variables  \\
    private static InventorySocketService instance;
    private InventoryPersistence inventoryPersistence;

    //  Constructor  \\
    private InventorySocketService() {
        inventoryPersistence = new InventorySocketPersistence(HOST, PORT);
    }

    //  Singleton Pattern  \\
    public static InventorySocketService getInstance() {
        if (instance == null)
            instance = new InventorySocketService();
        return instance;
    }

    /**
     * This method add a new inventory element to the list.
     * The elements is colocate and validate before been added.
     *
     * @param inventory to be added.
     * @return {@code true} if the inventory element has been added correctly. {@code false} Otherwise.
     */
    @Override
    public boolean add(Inventory inventory) throws ServiceException {
        if (inventory == null) {
            throw new ServiceException("The inventory is null.");
        }
        try {
            return inventoryPersistence.insert(inventory);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    /**
     * This method edits an inventory element that most be in the list.
     * If the inventory elements exists, is replaced.
     *
     * @param inventory to remove.
     * @return {@code true} if the inventory element has been modified. {@code false} Otherwise.
     */
    @Override
    public boolean edit(Inventory inventory) throws ServiceException {
        if (inventory == null) {
            throw new ServiceException("The inventory is null.");
        }
        try {
            return inventoryPersistence.update(inventory);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * This method removes an inventory element that most be in the list.
     * If the inventory elements exists, is removed.
     *
     * @param inventory to be remove.
     * @return {@code true} if the inventory element has been removed. {@code false} Otherwise.
     * @throws ServiceException When an error occurred.
     */
    @Override
    public boolean remove(Inventory inventory) throws ServiceException {
        if (inventory == null) {
            throw new ServiceException("The inventory is null.");
        }
        try {
            return inventoryPersistence.delete(inventory);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Removes all the inventories.
     *
     * @return  {@code true} if the inventories have been removed. {@code false} Otherwise.
     * @throws ServiceException When an error occurred.
     */
    public boolean removeAll() throws ServiceException {
        try {
            if (inventoryPersistence.deleteAll()) return true;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    /**
     * This method returns an inventory element if this exists one with that name.
     *
     * @param name of the inventory.
     * @return {@code Inventory} if the inventory element's name is in the list. {@code null} Otherwise.
     */
    @Override
    public Inventory get(String name) throws ServiceException {
        try {
            return inventoryPersistence.read(name);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Returns the list with all the inventories.
     *
     * @return {@code List<Inventory>} List with inventories.
     */
    @Override
    public List<Inventory> getAll() throws ServiceException {
        try {
            return inventoryPersistence.readAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
