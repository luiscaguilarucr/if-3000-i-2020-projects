package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;

public class Inventory {
    private String name;
    private List<Catalog> catalogs;

    /**
     * Object Inventory that contains catalogs.
     * @param name name of the catalog.
     * @param catalogs list of catalogs inside the inventory.
     */
    public Inventory(String name, List<Catalog> catalogs) {//TODO Valorar el uso de catalog...catalogs
        this.name = name;
        this.catalogs = catalogs;
    }

    /**
     * Add one or more Catalog to the list of catalogs related with this inventory.
     * @param catalogs One or more catalogs.
     */
    public void addCatalog(Catalog... catalogs){
        for (Catalog c: catalogs) {
            this.catalogs.add(c);
        }
    }

    /**
     * Removes a catalog by name.
     * @param name name of the catalog.
     */
    public void removeCatalog(String name){
        for(Catalog c: catalogs)
            if(c.getName().equalsIgnoreCase(name))
                catalogs.remove(c);
    }

    /**
     * Get a catalog using the name as reference.
     * @param name name of the catalog.
     * @return {@code Catalog} if the Catalog is exist, {@code null} otherwise.
     */
    public Catalog getCatalog(String name){
        for(Catalog c: catalogs)
            if(c.getName().equalsIgnoreCase(name))
                return c;
        return null;
    }

    /**
     * Gives the name of the inventory.
     * @return the name of the inventory.
     */
    public String getName() {
        return name;
    }

    public List<Catalog> getCatalogs() {//TODO valorar un Catalog[] como return
        return catalogs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }
}
