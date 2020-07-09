package edu.ucr.rp.programacion2.proyecto.util;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.Map;

public class CatalogConverter extends StringConverter<Catalog> {
    private Map<String, Catalog> catalogMap = new HashMap<>();

    @Override
    public String toString(Catalog catalog) {
        if(catalog == null){
            return null;
        }
        catalogMap.put(catalog.getName(), catalog);
        return catalog.getName();
    }

    @Override
    public Catalog fromString(String catalogName) {
        if (catalogName == null) {
            return null;
        }
        return catalogMap.get(catalogName);
    }
}
