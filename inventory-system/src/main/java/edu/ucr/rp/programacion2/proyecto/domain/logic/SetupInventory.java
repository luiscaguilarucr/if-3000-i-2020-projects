package edu.ucr.rp.programacion2.proyecto.domain.logic;

public class SetupInventory implements Setup<Inventory, String> {
    /**
     * This method renames a inventory
     * @param inventory
     * @param newName
     * @return true if the catalog was renamed, false if it was not
     */
    @Override
    public boolean rename(Inventory inventory, String newName) {
        if (!inventory.getName().equalsIgnoreCase(newName)) {
            inventory.setName(newName);
            return true;
        }
        return false;
    }
}
