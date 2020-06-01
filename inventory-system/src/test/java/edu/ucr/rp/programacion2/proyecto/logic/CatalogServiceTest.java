package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Item;
import edu.ucr.rp.programacion2.proyecto.util.Utility;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogServiceTest extends TestCase {

    private Inventory inventory;
    private CatalogService catalogService;

    @Before
    public void initialize() {

    }

    @Test
    public void testAddition() {
        //for(int i = 0 ; i < 5; i++){
        this.inventory = new Inventory("Carros");
        this.catalogService = new CatalogService(inventory);
        Catalog catalog = Utility.randomCatalog();
        catalog.setId(3);
        catalog.setName("Pick-ups");
        if (catalogService.add(catalog))
            System.out.println("Se guardo: " + catalog);
        else
            System.out.println("- No se guardo: " + catalog);

    }

    @Test
    public void testDelete() {
        this.inventory = new Inventory("Carros");
        this.catalogService = new CatalogService(inventory);
        Catalog catalog = Utility.randomCatalog();
        catalog.setId(4);
        catalog.setName("Sedan");
        if (catalogService.remove(catalog))
            System.out.println("Se elimino " + catalog);
        else
            System.out.println("- No se elimino: " + catalog);
    }

    @Test
    public void testEdit() {
        this.inventory = new Inventory("Aviones");
        this.catalogService = new CatalogService(inventory);
        Catalog catalog = Utility.randomCatalog();
        catalog.setId(3);
        catalog.setName("Pick-ups");
        List<Item> itemList = new ArrayList<>();
        Map<String, Object> features = new HashMap<>();

        features.put("Marca", "Ford");
        features.put("Modelo", "Ranger");
        features.put("Cant Pasajeros", "2");
        features.put("Puertas", "2");
        features.put("Tracción", "Automática");


        itemList.add(new Item("Ford Ranger", features));
        catalog.setItems(itemList);
        if (catalogService.edit(catalog))
            System.out.println("Se edito " + catalog);
        else
            System.out.println("- No se edito: " + catalog);
    }


    @Test
    public void testShow(){
        this.inventory = new Inventory("Carros");
        this.catalogService = new CatalogService(inventory);
        System.out.println(catalogService.getAll());
        System.out.println(catalogService.getAll().size());
        System.out.println(catalogService.getAll().get(1));
    }
}

