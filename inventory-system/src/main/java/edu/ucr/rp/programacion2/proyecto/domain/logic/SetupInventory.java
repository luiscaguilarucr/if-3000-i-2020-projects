package edu.ucr.rp.programacion2.proyecto.domain.logic;

public class SetupInventory implements Setup<Inventory, String> {
    @Override
    public boolean rename(Inventory inventory, String newName) {
        if (!inventory.getName().equalsIgnoreCase(newName)) {
            inventory.setName(newName);
            return true;
        }
        return false;
    }
}
