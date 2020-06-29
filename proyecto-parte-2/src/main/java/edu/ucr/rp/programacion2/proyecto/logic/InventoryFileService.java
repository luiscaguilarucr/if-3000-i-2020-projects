package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.persistance.InventoryFilePersistence;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryFileService implements InventoryService{
    //  Variables  \\
    private static InventoryFileService instance;
    private List<Inventory> list;
    private InventoryFilePersistence inventoryFilePersistence;
    //  Constructor  \\
    private InventoryFileService(){
        list = new ArrayList<>();
        inventoryFilePersistence = new InventoryFilePersistence();
        refresh();
    }
    //  Singleton Pattern  \\
    public static InventoryFileService getInstance(){
        if(instance == null)
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
    public boolean add(Inventory inventory)  throws ServiceException{
        refresh();
        if(validateAddition(inventory)){
            list.add(inventory);
            return inventoryFilePersistence.write(inventory);
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
            Inventory oldInventory = list.get(list.indexOf(inventory));
            return inventoryFilePersistence.rename(oldInventory.getName(), inventory.getName());
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
        return inventoryFilePersistence.delete(inventory);
    }

    public boolean removeAll()  throws ServiceException{
        list.clear();
        if (!inventoryFilePersistence.deleteAll()) return false;
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
        refresh();
        for(Inventory inventory: list)
            if(inventory.getName().equals(name))
                return inventory;

        return null;
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
     * Creates a list with the names of the inventories.
     *
     * @return  {@code List<String>} List with names of inventories.
     */
    public List<String> getNamesList() {
        List<String> namesList = new ArrayList();
        for(Inventory inventory : list)
            namesList.add(inventory.getName());
        return namesList;
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
    private boolean validateAddition(Inventory inventory) {
        if(inventory == null) return false;                         // Not null
        if(list.contains(inventory)) return false;                  // Unique ID
        return !containsByName(inventory.getName());                // Unique Name
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

    private Boolean refresh(){
        //Lee el archivo
        Object object = inventoryFilePersistence.read();
        //Valida que existe y lo sustituye por la lista en memoria
        if(object!=null){
            list = (List<Inventory>) object;
            return true;
        }
        return false;
    }
}
