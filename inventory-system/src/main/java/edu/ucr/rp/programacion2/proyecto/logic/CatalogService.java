package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.persistance.CatalogPersistence;
import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.idgenerator.IDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a service to manage the Catalogs.
 *
 * @version 2.0
 * Singleton Pattern added.
 */
public class CatalogService implements Service<Catalog, String, List> {
    private List<Catalog> list;
    private CatalogPersistence catalogPersistence;
    private IDGenerator idGenerator;

    //  Constructor \\
    public CatalogService(Inventory inventory) {
        list = new ArrayList<Catalog>();
        catalogPersistence = new CatalogPersistence(inventory.getName());
        refresh();
        idGenerator = new IDGenerator(inventory);
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
    public boolean add(Catalog catalog) {
        refresh();
        catalog.setId(idGenerator.get());
        if (validateAddition(catalog)) {
            catalog.setId(idGenerator.generate());
            list.add(catalog);
            catalogPersistence.write(catalog);
            return list.contains(catalog);
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
    public boolean edit(Catalog catalog) {
        refresh();
        if (validateEdition(catalog)) {
            list.add(list.indexOf(catalog), catalog);
            catalogPersistence.write(catalog);
            return true;
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
    public boolean remove(Catalog catalog) {
        refresh();
        if (catalog == null || !list.contains(catalog)) {
            return false;
        }
        list.remove(catalog);
        catalogPersistence.delete(catalog);
        return true;
    }

    public boolean removeAll() {
        if (!idGenerator.reset()) return false;
        list.clear();
        if (!catalogPersistence.deleteAll()) return false;
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
    public Catalog get(String name) {
        refresh();
        for (Catalog catalog : list)
            if (catalog.getName().equals(name))
                return catalog;

        return null;
    }

    /**
     * Returns the list with all the elements.
     *
     * @return {@code List<Catalog>} List with elements
     */
    @Override
    public List<Catalog> getAll() {
        refresh();
        return list;
    }

    /**
     * Creates a list with the names of the catalogs.
     *
     * @return  {@code List<String>} List with names of catalogs.
     */
    public List getNamesList(){
        List<String> namesList = new ArrayList();
        for(Catalog catalog : list)
            namesList.add(catalog.getName());
        return namesList;
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
    private boolean validateAddition(Catalog catalog) {
        if (catalog == null) return false;                         // Not null
        if (list.contains(catalog)) return false;                // Unique ID
        if (!validateSchema(catalog.getSchema())) return false;  // Valid schema
        return !containsByName(catalog.getName());              // Unique Name
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
    private boolean validateEdition(Catalog catalog) {
        if (catalog == null) return false;                         // Not null
        if (!list.contains(catalog)) return false;               // ID in list
        if (!validateSchema(catalog.getSchema())) return false;  // Valid schema
        return !nameUsedByOtherCatalog(catalog);                // Name used
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
    private boolean containsByName(String name) {
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
    private boolean nameUsedByOtherCatalog(Catalog catalog) {
        for (Catalog c : list)
            if (!c.equals(catalog))
                if (c.getName().equals(catalog.getName()))
                    return true;
        return false;
    }

    private Boolean refresh() {
        //Lee el archivo
        Object object = catalogPersistence.read();
        //Valida que existe y lo sustituye por la lista en memoria
        if (object != null) {
            list = (List<Catalog>) object;
            return true;
        }
        return false;
    }

}
