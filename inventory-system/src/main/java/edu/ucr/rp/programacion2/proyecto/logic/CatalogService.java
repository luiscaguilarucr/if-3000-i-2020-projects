package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;

import java.util.List;

/**
 * This is a service to manage the Catalogs.
 *
 * @version 2.0
 * @implNote This has an singleton Pattern.
 */
public class CatalogService implements Service<Catalog, String, List<Catalog>> {
    //  Variables  \\
    private static CatalogService instance;
    private List<Catalog> list;
    //  Constructor \\
    private CatalogService() { }
    //  Singleton Pattern  \\
    public static CatalogService getInstance() {
        if (instance == null) {
            instance = new CatalogService();
        }
        return instance;
    }
    //  Methods  \\
    /**
     * This method add a new element to the list.
     * The elements is colocate and validate before been added.
     *
     * @param element to be added.
     * @return {@code true} if the element has been added correctly. {@code false} Otherwise.
     */
    @Override
    public boolean add(Catalog element) {
        if (validateAddition(element)) {
            // Generate ID
            list.add(element);
            //catalogPersistence.save(catalogs); //TODO salvar en el archivo
            // comprobate addition.
            return true;
        }
        return false;
    }

    /**
     * This method edits an element that most be in the list.
     * If the elements exists, is replaced.
     *
     * @param element to remove.
     * @return {@code true} if the element has been modified. {@code false} Otherwise.
     */
    @Override
    public boolean edit(Catalog element) {
        if(validateEdition(element))
        list.add(list.indexOf(element), element);
        //catalogPersistence.save(catalogs); //TODO salvar en el archivo
        return true;
    }

    /**
     * This method removes an element that most be in the list.
     * If the elements exists, is removed.
     *
     * @param element to be remove.
     * @return {@code true} if the element has been removed. {@code false} Otherwise.
     */
    @Override
    public boolean remove(Catalog element) {
        if (element == null || !list.contains(element)) {//TODO check
            return false;
        }
        list.remove(element);
        //catalogPersistence.remove(catalogs); //TODO eliminar el archivo
        return true;
    }
    /**
     * This method returns an element if this exists in the list.
     *
     * @param name of the catalog.
     * @return {@code Catalog} if the element's name is in the list. {@code null} Otherwise.
     */
    @Override
    public Catalog get(String name) {
        for(Catalog catalog: list)
            if(catalog.getName().equals(name))
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
        return list;
    }


    //  More methods \\
    /**
     * Check if the catalog can be added.
     *
     * Validations:
     * - Most have an unique id.
     * - Most have a valid schema.
     * - The name can't be repeated.
     *
     * @param catalog to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateAddition(Catalog catalog) {
        if(catalog==null) return false;                         // Not null
        if(list.contains(catalog)) return false;                // Unique ID
        if(!validateSchema(catalog.getSchema())) return false;  // Valid schema
        return !containsByName(catalog.getName());              // Unique Name
    }

    /**
     * Check if the catalog can be editing.
     *
     * Validations:
     * - Most exists in the list.
     * - Most have a valid schema.
     * - The name can't be repeated.
     *
     * @param catalog to be validate.
     * @return {@code true} if the element is valid. {@code false} otherwise.
     */
    private boolean validateEdition(Catalog catalog){
        if(catalog==null) return false;                         // Not null
        if(!list.contains(catalog)) return false;               // ID in list
        if(!validateSchema(catalog.getSchema())) return false;  // Valid schema
        return !nameUsedByOtherCatalog(catalog);                // Name used
    }

    /**
     * Validates the schema of one Catalog.
     *
     * Validations:
     * - Most have a schema and, at least, one property defined.
     *
     * @param schema to verify.
     * @return {@code true} if the schema is valid. {@code false} otherwise.
     */
    private boolean validateSchema(List<String> schema){
        return schema!=null && !schema.isEmpty();
    }
    /**
     * Checks if the name has been used by one Catalog.
     *
     * @param name to search.
     * @return {@code true} if the name is used. {@code false} otherwise.
     */
    private boolean containsByName(String name) {
        for (Catalog c : list)
            if (c.getName().equals(name))
                return false;
        return true;
    }
    /**
     * Checks if the name is used used by other Catalog.
     *
     * @param catalog to search.
     * @return {@code true} if the name is used by other catalog. {@code false} otherwise.
     */
    private boolean nameUsedByOtherCatalog(Catalog catalog) {
        if(catalog==null) return false;

        for (Catalog c : list)
            if(c!=catalog)
                if (c.getName().equals(catalog.getName()))
                    return false;
        return true;
    }
}
