package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.persistance.CatalogFilePersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.CatalogPersistence;
import edu.ucr.rp.programacion2.proyecto.persistance.PersistenceException;
import edu.ucr.rp.programacion2.proyecto.util.idgenerator.IDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a service to manage the Catalogs.
 *
 * @version 2.0
 * Singleton Pattern added.
 */
public class CatalogFileService implements CatalogService {
    //  Variables  \\
    private static CatalogFileService instance;
    private List<Catalog> list;
    private CatalogPersistence catalogPersistence;
    private IDGenerator idGenerator;
    private Inventory inventory;

    //  Constructor  \\
    private CatalogFileService() {
        list = new ArrayList<>();
        catalogPersistence = CatalogFilePersistence.getInstance();
        idGenerator = IDGenerator.getInstance();
        refresh();
    }

    //  Singleton Pattern  \\
    public static CatalogFileService getInstance() {
        if (instance == null)
            instance = new CatalogFileService();
        return instance;
    }

    /**
     * This method add a new element to the list.
     * The elements is colocate and validate before been added.
     *
     * @param catalog to be added.
     * @return {@code true} if the element has been added correctly. {@code false} Otherwise.
     */
    @Override
    public boolean add(Catalog catalog) throws ServiceException {
        refresh();
        catalog.getConfiguration().setId(idGenerator.get());
        if (validateAddition(catalog)) {
            catalog.getConfiguration().setId(idGenerator.generate());
            list.add(catalog);
            try {
                return catalogPersistence.write(catalog);
            } catch (PersistenceException e) {
                throw new ServiceException("Catalogs can't be added, because " + e.getMessage());
            }
        }
        return false;
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
        refresh();
        if (validateEdition(catalog)) {
            try {
                if (catalogPersistence.delete(list.get(list.indexOf(catalog)))) { // Delete the old value
                    list.add(catalog);
                    return catalogPersistence.write(catalog); //Write the newOne
                }
            } catch (PersistenceException e) {
                throw new ServiceException("Catalog can't be edited, because " + e.getMessage());
            }
        }
        return false;
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
        refresh();
        if (catalog == null || !list.contains(catalog)) {
            return false;
        }
        list.remove(catalog);
        try {
            return catalogPersistence.delete(catalog);
        } catch (PersistenceException e) {
            throw new ServiceException("Catalog can't be deleted, because " + e.getMessage());
        }
    }

    public boolean removeAll() throws ServiceException {
        if (!idGenerator.reset()) return false;
        list.clear();
        try {
            if (!catalogPersistence.deleteAll()) return false;
        } catch (PersistenceException e) {
            throw new ServiceException("Catalogs can't be deleted, because " + e.getMessage());
        }
        refresh();
        return list.isEmpty();
    }

    /**
     * This method returns an element if this exists in the list.
     *
     * @param name of the catalog.
     * @return {@code Catalog} if the element's name is in the list. {@code null} Otherwise.
     */
    @Override
    public Catalog get(String name) throws ServiceException {
        refresh();
        for (Catalog catalog : list) {
            if (catalog.getName().equals(name)) {
                return catalog;
            }
        }
        throw new ServiceException("Catalog named " + name + " is not found.");
    }

    /**
     * Returns the list with all the elements.
     *
     * @return {@code List<Catalog>} List with elements
     */
    @Override
    public List<Catalog> getAll() throws ServiceException {
        refresh();
        return list;
    }

    //  More methods \\

    /**
     * Check if the catalog can be added.
     * <p>
     * Validations:
     * - Most have an unique id.
     * - Most have a valid schema.
     * - The name can't be repeated.
     *
     * @param catalog to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateAddition(Catalog catalog) throws ServiceException {
        if (catalog == null)
            throw new ServiceException("the catalog is null.");                             // Not null
        if (list.contains(catalog))
            throw new ServiceException("the catalog already exists.");                      // Unique ID
        validateSchema(catalog.getSchema());                                                // Valid schema
        nameUsedByOtherCatalog(catalog);                                                    // Unique Name
        return true;
    }

    /**
     * Check if the catalog can be editing.
     * <p>
     * Validations:
     * - Most exists in the list.
     * - Most have a valid schema.
     * - The name can't be repeated.
     *
     * @param catalog to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateEdition(Catalog catalog) throws ServiceException {
        if (catalog == null)
            throw new ServiceException("the catalog is null.");                            // Not null
        if (!list.contains(catalog))
            throw new ServiceException("the catalog doesn't exist.");                      // ID in list
        validateSchema(catalog.getSchema());
        nameUsedByOtherCatalog(catalog);                                                   // Name used
        return true;
    }

    /**
     * Validates the schema of one Catalog.
     * <p>
     * Validations:
     * - Most have a schema and, at least, one property defined.
     *
     * @param schema to verify.
     * @return {@code true} if the schema is valid. {@code false} otherwise.
     */
    private boolean validateSchema(List<String> schema) {
        return schema != null && !schema.isEmpty();
    }

    /**
     * Checks if the name has been used by one Catalog.
     *
     * @param name to search.
     * @return {@code true} if the name is used. {@code false} otherwise.
     */
    private boolean containsByName(String name) throws ServiceException {
        for (Catalog c : list)
            if (name.equalsIgnoreCase(c.getName()))
                return true;
        return false;
    }

    /**
     * Checks if the name is used used by other Catalog.
     *
     * @param catalog to search.
     * @return {@code true} if the name is used by other catalog. {@code false} otherwise.
     */
    private boolean nameUsedByOtherCatalog(Catalog catalog) throws ServiceException {
        for (Catalog c : list) {
            if (!c.equals(catalog)) {
                if (c.getName().equals(catalog.getName())) {
                    throw new ServiceException("the catalog's name is already used.");
                }
            }
        }
        return false;
    }

    private Boolean refresh() {
        //Lee el archivo
        List<Catalog> catalogs = null;
        try {
            catalogs = catalogPersistence.read();
            if (catalogs != null) {
                list = catalogs;
                return true;
            }
        } catch (PersistenceException | NullPointerException e) {
            System.out.println("Error: Catalog/refresh: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Inventory getInventory() throws ServiceException{
        return inventory;
    }

    @Override
    public void setInventory(Inventory inventory) throws ServiceException{
        try {
            idGenerator.setInventory(inventory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        catalogPersistence.setInventory(inventory);
        this.inventory = inventory;
    }
}
