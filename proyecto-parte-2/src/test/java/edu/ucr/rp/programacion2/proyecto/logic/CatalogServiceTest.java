package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.Configuration;
import edu.ucr.rp.programacion2.proyecto.util.Utility;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CatalogServiceTest extends TestCase {

    private Inventory inventory;
    private CatalogService catalogService;

    @Test
    public void testAddition() throws ServiceException {
        inventory = new Inventory("Carros");
        catalogService = CatalogFileService.getInstance();
        catalogService.setInventory(inventory);
        for(int i = 0 ; i < 5; i++) {
            Catalog catalog = Utility.randomCatalog();
            if (catalogService.add(catalog))
                System.out.println("Se guardo: " + catalog);
            else
                System.out.println("No se guardo: " + catalog);
        }

    }

  //  @Test
    public void testDelete() throws ServiceException {
        this.inventory = new Inventory("Carros");
        catalogService = CatalogFileService.getInstance();
        for(int i = 0 ; i < 100; i++) {
            Catalog catalog = Utility.randomCatalog();
            catalog.setConfiguration(new Configuration(new Random().nextInt(15)));
            if (catalogService.remove(catalog))
                System.out.println("Se elimino: " + catalog);
            else
                System.out.println("- No se elimino: " + catalog);
        }
    }

    @Test
    public void testEdit() throws ServiceException {
        this.inventory = new Inventory("Carros");
        catalogService = CatalogFileService.getInstance();

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
        List<Map> items = new ArrayList<>();

        for(int i = 0 ; i < 10; i++) {
            Map item = Utility.randomItem();

            if(!items.contains(item)){
                items.add(item);
               // System.out.println("Se agregó el item [" + item  + "].");
            }
            //System.out.println("No se agregó el item [" + item  + "] ya existe en la lista.");
        }
        return items;
    }
  //  @Test
    public void testShow() throws ServiceException {
        this.inventory = new Inventory("AUDI");
        catalogService = CatalogFileService.getInstance();
        System.out.println(catalogService.getAll());
        System.out.println(catalogService.getAll().size());
        System.out.println(catalogService.get("SUVs"));
    }
    @Test
    public void testDeleteAll() throws ServiceException {
        this.inventory = new Inventory("Carros");
        catalogService = CatalogFileService.getInstance();
        if (catalogService.removeAll())
            System.out.println("Se eliminaron los Catalogos de " + inventory.getName());
        else
            System.out.println("- No se elimino los Catalogos de " + inventory.getName());
    }
}

