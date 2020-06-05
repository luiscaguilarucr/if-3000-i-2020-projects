package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Item;

public class SetupCatalog implements Setup<Catalog, String> {

    /**
     * This method renames a catalog
     * @param catalog
     * @param newName
     * @return true if the catalog was renamed, false if it was not
     */
    @Override
    public boolean rename(Catalog catalog, String newName) {
        if (!catalog.getName().equalsIgnoreCase(newName)) {
            catalog.setName(newName);
            return true;
        }
        return false;
    }

    /**
     * This method adds an item in a specific catalog
     * @param catalog
     * @param item
     * @return true if the item was added to the catalogue, false if it was not
     */
    public Catalog addItem(Catalog catalog, Item item) {
        if (!catalog.getItems().contains(item)){
            catalog.getItems().add(item);
            return catalog;
        }
        return null;
    }

    /**
     * This method removes an item in a specific catalog
     * @param catalog
     * @param item
     * @return true if the item was removed from the catalogue, false if it was not
     */
    public Catalog removeItem(Catalog catalog, Item item) {
        if (catalog.getItems().contains(item) && item != null) {
            catalog.getItems().remove(item);
            return catalog;
        }
        return null;
    }

    /**
     * This method modifies an item in a specific catalog
     * @param catalog
     * @param previousItem
     * @param newItem
     * @return true if the item in the catalogue was modified, false if it was not
     */
    public boolean modifyItem(Catalog catalog, Item previousItem, Item newItem) {
        if (catalog.getItems().contains(previousItem) && newItem != null) {
            catalog.getItems().add(catalog.getItems().indexOf(previousItem), newItem);
            return true;
        }
        return false;
    }
}
