package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.Item;
import edu.ucr.rp.programacion2.proyecto.util.Utility;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CatalogServiceTest extends TestCase {

    private Inventory inventory;
    private CatalogService catalogService;

    @Test
    public void testAddition() {
        inventory = new Inventory("Carros");
        catalogService = new CatalogService(inventory);
        for(int i = 0 ; i < 10; i++) {
            Catalog catalog = Utility.randomCatalog();
            if (catalogService.add(catalog))
                System.out.println("Se guardo: " + catalog);
            else
                System.out.println("No se guardo: " + catalog);
        }

    }

  //  @Test
    public void testDelete() {
        this.inventory = new Inventory("Carros");
        catalogService = new CatalogService(inventory);
        for(int i = 0 ; i < 100; i++) {
            Catalog catalog = Utility.randomCatalog();
            catalog.setId(Utility.random(10));
            if (catalogService.remove(catalog))
                System.out.println("Se elimino: " + catalog);
            else
                System.out.println("- No se elimino: " + catalog);
        }
    }

    @Test
    public void testEdit() {
        this.inventory = new Inventory("Carros");
        catalogService = new CatalogService(inventory);


        for(int i = 0 ; i < 5; i++) {
            Catalog catalog = catalogService.get(Utility.randomCatalogName());
            if(catalog==null) continue;
            List items = randomItemList();
            catalog.setItems(items);
            if (catalogService.edit(catalog))
                System.out.println("Se edito: " + catalog);
            else
                System.out.println("- No se edito: " + catalog);
        }
    }

    private List randomItemList(){
        List<Item> items = new ArrayList<>();

        for(int i = 0 ; i < 10; i++) {
            Item item = Utility.randomItem();

            if(!items.contains(item)){
                items.add(item);
               // System.out.println("Se agregó el item [" + item  + "].");
            }
            //System.out.println("No se agregó el item [" + item  + "] ya existe en la lista.");
        }
        return items;
    }
  //  @Test
    public void testShow(){
        this.inventory = new Inventory("Carros");
        catalogService = new CatalogService(inventory);
        System.out.println(catalogService.getAll());
        System.out.println(catalogService.getAll().size());
        System.out.println(catalogService.get("Sedan"));
    }
    @Test
    public void testDeleteAll(){
        this.inventory = new Inventory("Carros");
        catalogService = new CatalogService(inventory);
        if (catalogService.removeAll())
            System.out.println("Se eliminaron los Catalogos de " + inventory.getName());
        else
            System.out.println("- No se elimino los Catalogos de " + inventory.getName());
    }
}

