package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.persistance.InventoryFilePersistence;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.InventoryPersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.PersistenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryFileService implements InventoryService {
    //  Variables  \\
    private static InventoryFileService instance;
    private List<Inventory> list;
    private InventoryPersistence inventoryPersistence;

    //  Constructor  \\
    private InventoryFileService() {
        list = new ArrayList<>();
        inventoryPersistence = new InventoryFilePersistence();
        refresh();
    }

    //  Singleton Pattern  \\
    public static InventoryFileService getInstance() {
        if (instance == null)
            instance = new InventoryFileService();
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
        refresh();
        inventory.setId(UUID.randomUUID());
        if (validateAddition(inventory)) {
            list.add(inventory);
            try {
                return inventoryPersistence.write(inventory);
            } catch (PersistenceException e) {
                throw new ServiceException("Inventory can't be saved, because " + e.getMessage());
            }
        }
        return false;
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
        refresh();
        if (validateEdition(inventory)) {
            try {
                return inventoryPersistence.write(inventory);
            } catch (PersistenceException e) {
                throw new ServiceException("Inventory can't be edited, because " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * This method removes an inventory element that most be in the list.
     * If the inventory elements exists, is removed.
     *
     * @param inventory to be remove.
     * @return {@code true} if the inventory element has been removed. {@code false} Otherwise.
     */
    @Override
    public boolean remove(Inventory inventory) throws ServiceException {
        refresh();
        if (inventory == null || !list.contains(inventory)) {
            return false;
        }
        list.remove(inventory);
        try {
            return inventoryPersistence.delete(inventory);
        } catch (PersistenceException e) {
            throw new ServiceException("Inventory can't be deleted, because " + e.getMessage());

        }
    }

    public boolean removeAll() throws ServiceException {
        list.clear();
        try {
            if (!inventoryPersistence.deleteAll()) return false;
        } catch (PersistenceException e) {
            throw new ServiceException("Inventories can't be deleted, because " + e.getMessage());
        }
        refresh();
        return list.isEmpty();
    }

    /**
     * This method returns an inventory element if this exists in the list.
     *
     * @param name of the inventory.
     * @return {@code Inventory} if the inventory element's name is in the list. {@code null} Otherwise.
     */
    @Override
    public Inventory get(String name) throws ServiceException {
        refresh();
        for (Inventory inventory : list)
            if (inventory.getName().equals(name))
                return inventory;

        throw new ServiceException("Inventory named " + name + " is not found.");
    }

    /**
     * Returns the list with all the inventory elements.
     *
     * @return {@code List<Inventory>} List with inventory elements
     */
    @Override
    public List<Inventory> getAll() throws ServiceException {
        refresh();
        return list;
    }

    /**
     * Check if the inventory can be added.
     * <p>
     * Validations:
     * - Most have an unique name.
     * - The name can't be repeated.
     *
     * @param inventory to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateAddition(Inventory inventory) throws ServiceException {
        if (inventory == null)
            throw new ServiceException("the inventory is null.");                                         // Not null
        if (list.contains(inventory))
            throw new ServiceException("the inventory already exists.");                           // Unique ID
        if (containsByName(inventory.getName()))
            throw new ServiceException("the inventory already exists.");              // Unique Name
        return true;
    }

    /**
     * Check if the inventory can be editing.
     * <p>
     * Validations:
     * - Most exists in the list.
     * - The name can't be repeated.
     *
     * @param inventory to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateEdition(Inventory inventory) throws ServiceException {
        if (inventory == null) throw new ServiceException("the inventory is null.");        // Not null
        if (!list.contains(inventory))
            throw new ServiceException("the inventory doesn't exist.");                           // Unique ID
        if (nameUsedByOtherInventory(inventory))
            throw new ServiceException("the inventory's name is already used.");              // Unique Name
        return true;
    }

    /**
     * Checks if the name has been used by one inventory.
     *
     * @param name to search.
     * @return {@code true} if the name is used. {@code false} otherwise.
     */
    private boolean containsByName(String name) {
        for (Inventory i : list)
            if (name.equalsIgnoreCase(i.getName()))
                return true;
        return false;
    }

    /**
     * Checks if the name is used used by other inventory.
     *
     * @param inventory to search.
     * @return {@code true} if the name is used by other inventory. {@code false} otherwise.
     */
    private boolean nameUsedByOtherInventory(Inventory inventory) {
        for (Inventory i : list)
            if (!i.equals(inventory))
                if (i.getName().equals(inventory.getName()))
                    return true;
        return false;
    }

    private Boolean refresh() {
        //Lee el archivo
        List<Inventory> inventoryList = null;
        try {
            inventoryList = inventoryPersistence.read();
            if (inventoryList != null) {
                list = inventoryList;
                return true;
            }
        } catch (PersistenceException e) {
            System.out.println("Error: Inventory/refresh: " + e.getMessage());
        }
        return false;
    }
}
