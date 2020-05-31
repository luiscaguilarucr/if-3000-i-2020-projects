package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;

public class CatalogService implements Service<Inventory, String> {
    private static CatalogService instance;

    private List<Inventory> catalogs;

    private CatalogService(){

    }

    public static CatalogService getInstance(){
        if( instance == null){
            instance = new CatalogService();
        }
        return instance;
    }

    //CatalogPersistence catalogPersistence = new CatalogPersistence();
    @Override
    public boolean add(Inventory object) {
        if (validateAddition(object)) {
            catalogs.add(object); //guardar el catálogo en la lista
            //catalogPersistence.save(catalogs); //TODO salvar en el archivo
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Inventory object) {
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
    public boolean modify(Inventory object) {
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
    public Inventory get(String name) {
        //tiene que existir
        Inventory temp = new Inventory(name);
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

    private boolean validateAddition(Inventory object) {
        //para que se agregue no debe que existir otro igual
        //debe tener al menos una propiedad
        //validar que tenga nombre
        if (object == null || catalogs.contains(object) || validateCatalogName(object)) {
            return false;
        } /*else if (object.getSchema() == null || object.getSchema().isEmpty()) {
            return false;
        }*/
        return true;
    }

    private boolean validateCatalogName(Inventory object) {
        //TODO validar espacios vacíos
        return "".equals(object.getName());
    }
}
