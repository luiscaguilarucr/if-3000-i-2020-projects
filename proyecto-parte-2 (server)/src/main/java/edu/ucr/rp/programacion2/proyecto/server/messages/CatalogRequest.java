package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;

public class CatalogRequest {
    // Variable
    private Catalog catalog;
    // Methods
    public Catalog getCatalog() {
        return catalog;
    }

    public CatalogRequest setCatalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }
}
