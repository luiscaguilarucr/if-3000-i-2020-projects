package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.CatalogPersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.CatalogSocketPersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a service to manage the Catalogs.
 *
 * @version 2.0
 * Singleton Pattern added.
 */
public class CatalogSocketService implements CatalogService {
    private CatalogSocketPersistence catalogPersistence;
    private Inventory inventory;
    private static CatalogSocketService instance;

    //  Constructor \\
    private CatalogSocketService() {
        catalogPersistence = new CatalogSocketPersistence("127.0.0.1", 12121);
    }

    public static CatalogSocketService getInstance(){
        if(instance == null){
            instance = new CatalogSocketService();
        }
        return instance;
    }

    //  Methods  \\
    /**
     * This method add a new element to the list.
     * The elements is colocate and validate before been added.
     *
     * @param catalog to be added.
     * @return {@code true} if the element has been added correctly. {@code false} Otherwise.
     */
    @Override
    public boolean add(Catalog catalog) throws ServiceException {
        if (catalog == null) {
            throw new ServiceException("The catalog is null.");
        }
        try {
            return catalogPersistence.insert(catalog);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * This method edits an element that most be in the list.
     * If the elements exists, is replaced.
     *
     * @param catalog to remove.
     * @return {@code true} if the element has been modified. {@code false} Otherwise.
     */
    @Override
    public boolean edit(Catalog catalog) throws ServiceException {
        if (catalog == null) {
            throw new ServiceException("The catalog is null.");
        }
        try {
            return catalogPersistence.update(catalog);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * This method removes an element that most be in the list.
     * If the elements exists, is removed.
     *
     * @param catalog to be remove.
     * @return {@code true} if the element has been removed. {@code false} Otherwise.
     */
    @Override
    public boolean remove(Catalog catalog) throws ServiceException {
        if (catalog == null) {
            throw new ServiceException("The catalog is null.");
        }
        try {
            return catalogPersistence.delete(catalog);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean removeAll() throws ServiceException {
        try {
            if (catalogPersistence.deleteAll()) return true;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    /**
     * This method returns an element if this exists in the list.
     *
     * @param name of the catalog.
     * @return {@code Catalog} if the element's name is in the list. {@code null} Otherwise.
     */
    @Override
    public Catalog get(String name) throws ServiceException {
        try {
            return catalogPersistence.read(name);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Returns the list with all the elements.
     *
     * @return {@code List<Catalog>} List with elements
     */
    @Override
    public List<Catalog> getAll() throws ServiceException {
        try {
            return catalogPersistence.readAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        catalogPersistence.setInventory(inventory);
        this.inventory = inventory;
    }
}
