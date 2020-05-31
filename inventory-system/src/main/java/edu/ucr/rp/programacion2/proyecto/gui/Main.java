package edu.ucr.rp.programacion2.proyecto.gui;

import edu.ucr.rp.programacion2.proyecto.business_rules.io.CatalogPersistence;
import edu.ucr.rp.programacion2.proyecto.business_rules.io.InventoryPersistence;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;

import java.io.File;

public class Main {
    public static void main(String...args){
        App app = new App();
        app.display();

    }
}
