package edu.ucr.rp.programacion2.proyecto.util.idgenerator;

import edu.ucr.rp.programacion2.proyecto.util.inventorycontrol.ConfigurationPersistence;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

public class IDGenerator implements Generable<Integer> {
    private Integer counter;
    private ConfigurationPersistence persistence;

    public IDGenerator(Inventory inventory) {
        this.counter = 0;
        this.persistence = new ConfigurationPersistence(inventory.getName());
        refresh();
    }

    /**
     * This methods returns and increase the ID counter.
     *
     * @return {@code Integer} ID.
     */
    @Override
    public Integer generate() {
        save(++counter);
        return counter;
    }

    /**
     * This methods returns and without increase the ID counter.
     *
     * @return {@code Integer} ID.
     */
    @Override
    public Integer get() {
        return counter + 1;
    }


    /**
     * Reset the counter to zero.
     *
     * @return {@code true} the counter has become zero, {@code false} an error has occurred.
     */
    public boolean reset() {
        counter = 0;
        save(counter);
        return counter.equals(0);
    }

    private void save(Integer counter) {
        persistence.write(counter);
    }


    private Boolean refresh() {
        //Lee el archivo
        Integer object = persistence.read();
        //Valida que existe y lo sustituye por la id en memoria
        if (object != null) {
            counter = object;
            return true;
        }
        return false;
    }
}
