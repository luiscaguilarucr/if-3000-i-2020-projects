package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.InventoryPersistance;
import edu.ucr.rp.programacion2.proyecto.persistance.InventorySocketPersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class InventorySocketService implements InventoryService{
    //  Variables  \\
    private static InventorySocketService instance;
    private List<Inventory> list;
    private InventoryPersistance inventoryPersistance;
    //  Constructor  \\
    private InventorySocketService(){
        list = new ArrayList<>();
        inventoryPersistance = new InventorySocketPersistence("127.0.0.1", 12121);
        try {
            refresh();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
    //  Singleton Pattern  \\
    public static InventorySocketService getInstance(){
        if(instance == null)
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
    public boolean add(Inventory inventory)  throws ServiceException{
        refresh();
        if(validateAddition(inventory)){
            list.add(inventory);
            try {
                return inventoryPersistance.insert(inventory);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage());
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
    public boolean edit(Inventory inventory)  throws ServiceException{
        refresh();
        if(validateEdition(inventory)){
            try {
                return inventoryPersistance.update(inventory);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        return false;
    }//TODO evaluate how to change name. or identify witch object was selected. [id generator].

    /**
     * This method removes an inventory element that most be in the list.
     * If the inventory elements exists, is removed.
     *
     * @param inventory to be remove.
     * @return {@code true} if the inventory element has been removed. {@code false} Otherwise.
     */
    @Override
    public boolean remove(Inventory inventory)  throws ServiceException{
        refresh();
        if (inventory == null || !list.contains(inventory)) {
            return false;
        }
        list.remove(inventory);
        try {
            return inventoryPersistance.delete(inventory);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean removeAll()  throws ServiceException{
        list.clear();
        try {
            if (!inventoryPersistance.deleteAll()) return false;
        } catch (edu.ucr.rp.programacion2.proyecto.persistance.PersistenceException e) {
            e.printStackTrace();
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
    public Inventory get(String name)  throws ServiceException{
        try {
            return inventoryPersistance.read(name);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Returns the list with all the inventory elements.
     *
     * @return {@code List<Inventory>} List with inventory elements
     */
    @Override
    public List<Inventory> getAll()  throws ServiceException{
        refresh();
        return list;
    }
    /**
     * Check if the inventory can be added.
     *
     * Validations:
     * - Most have an unique name.
     * - The name can't be repeated.
     *
     * @param inventory to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateAddition(Inventory inventory) throws ServiceException{
        if(inventory == null) throw new ServiceException("the inventory is null.");                                         // Not null
        if(list.contains(inventory)) throw new ServiceException("the inventory already exists.");                           // Unique ID
        if(containsByName(inventory.getName()))   throw new ServiceException("the inventory already exists.");              // Unique Name
        return true;
    }

    /**
     * Check if the inventory can be editing.
     *
     * Validations:
     * - Most exists in the list.
     * - The name can't be repeated.
     *
     * @param inventory to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateEdition(Inventory inventory){
        if(inventory==null) return false;                         // Not null
        if(!list.contains(inventory)) return false;               // ID in list
        return !nameUsedByOtherInventory(inventory);              // Name used
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
            if(!i.equals(inventory))
                if (i.getName().equals(inventory.getName()))
                    return true;
        return false;
    }

    private Boolean refresh() throws ServiceException{
        //Lee el archivo
        Object object = null;
        try {
            object = inventoryPersistance.readAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
        //Valida que existe y lo sustituye por la lista en memoria
        if(object!=null){
            list = (List<Inventory>) object;
            return true;
        }
        return false;
    }
}
