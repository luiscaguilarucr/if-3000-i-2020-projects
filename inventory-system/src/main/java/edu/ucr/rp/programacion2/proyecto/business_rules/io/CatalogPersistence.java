package edu.ucr.rp.programacion2.proyecto.business_rules.io;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CatalogPersistence {
    File file = new File("inventory/catalogs");


    public
    CatalogPersistence(File file) {
        this.file = file;
    }

    /**
     *
     * @param catalog guarda un catalogo.
     * @return {@<code>true</code>} Si se guardó correctamente {@<code>false</code>} no se guardo.
     */
    public Boolean save(Catalog catalog){
        //Crear dir con nombre Tornillos
        //Guarda itmes
        //Guarda schema
        return true;//TODO
    }

    public Boolean write(List attribute){
        return false;
    }
    public List readAll(){
        ArrayList list = new ArrayList();
        File[] catalogs = file.listFiles(); //Nombres de los archivos.
        //Clavos
        //Martillos
        //Por cada catalogo que exista en el directorio
        //leer el esquema
        //read(catalog, schema) //catalog, Schema.json
        //leer catalog con items
        //read(catalog, items) //catalogItems.json
        //crear el catalogo
        //Lo guarda en arreglo

        for(File c: catalogs){
            List schema = read(c.getName(), "schema.json");//TODO cambiar a constante SCHEMA+EXT  // schema.json
            List items = read(c.getName(), "items.json");//TODO cambiar a constante SCHEMA+EXT  // schema.json

            //Crear Catalog
        //    Catalog catalog = new Catalog(c.getName(), items, schema);
           // list.add();
        }


        return new ArrayList();//TODO
    }

    /**
     *
     * @param catalog nombre del catálogo.
     * @param attribute atributo a leer.
     * @return Properties: List
     * @return Items: List
     */
    public List read(String catalog, String attribute){
            //catalog+attribute);
            //jsonRead(catalog+attribute)
        return null;//TODO
    }
    public Boolean remove(Catalog catalog){
        return false;
    }
    public Boolean removeAll(){
        return false;
    }
}
