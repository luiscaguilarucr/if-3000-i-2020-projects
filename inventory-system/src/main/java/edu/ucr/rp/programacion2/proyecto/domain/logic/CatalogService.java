package edu.ucr.rp.programacion2.proyecto.domain.logic;

import edu.ucr.rp.programacion2.proyecto.business_rules.io.CatalogPersistence;

import java.util.List;

public class CatalogService implements Service<Catalog, String> {
    private List<Catalog> catalogs;

    //CatalogPersistence catalogPersistence = new CatalogPersistence();
    @Override
    public boolean add(Catalog object) {
        if (validateAddition(object)) {
            catalogs.add(object); //guardar el catálogo en la lista
            //catalogPersistence.save(catalogs); //TODO salvar en el archivo
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Catalog object) {
        //debe existir el catálogo
        //el objeto debe ser un catálogo
        if (object == null || !catalogs.contains(object)) {
            return false;
        }
        catalogs.remove(object); //elimina de la lista
        //catalogPersistence.remove(catalogs); //TODO eliminar el archivo
        return true;
    }

    @Override
    public boolean modify(Catalog object) {
        //tiene que existir
        //sea diferente de null
        if (object == null || !catalogs.contains(object)) {
            return false;
        }
        catalogs.add(catalogs.indexOf(object), object);
        //catalogPersistence.save(catalogs); //TODO salvar en el archivo
        return true;
    }

    @Override
    public Catalog get(String name) {
        //tiene que existir
        Catalog temp = new Catalog(name, null, null);
        int index = catalogs.indexOf(temp);
        if (index == -1) {
            return null;
        }
        return catalogs.get(index);
    }

    @Override
    public List getAll() {
        return catalogs;
    }

    private boolean validateAddition(Catalog object) {
        //para que se agregue no debe que existir otro igual
        //debe tener al menos una propiedad
        //validar que tenga nombre
        if (object == null || catalogs.contains(object) || validateCatalogName(object)) {
            return false;
        } else if (object.getSchema() == null || object.getSchema().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateCatalogName(Catalog object) {
        //TODO validar espacios vacíos
        return "".equals(object.getName());
    }
}
