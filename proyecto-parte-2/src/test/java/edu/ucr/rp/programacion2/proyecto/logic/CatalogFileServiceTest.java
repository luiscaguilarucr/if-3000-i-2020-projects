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

public class CatalogFileServiceTest extends TestCase {

    private Inventory inventory;
    private CatalogFileService catalogFileService;

    @Test
    public void testAddition() throws ServiceException {
        inventory = new Inventory("Carros");
        catalogFileService = CatalogFileService.getInstance();
        catalogFileService.setInventory(inventory);

        for(int i = 0 ; i < 5; i++) {
            Catalog catalog = Utility.randomCatalog();
            if (catalogFileService.add(catalog))
                System.out.println("Se guardo: " + catalog);
            else
                System.out.println("No se guardo: " + catalog);
        }

    }

  //  @Test
    public void testDelete() throws ServiceException {
        this.inventory = new Inventory("Carros");
        catalogFileService = CatalogFileService.getInstance();
        catalogFileService.setInventory(inventory);
        for(int i = 0 ; i < 100; i++) {
            Catalog catalog = Utility.randomCatalog();
            catalog.setConfiguration(new Configuration(new Random().nextInt(15)));
            if (catalogFileService.remove(catalog))
                System.out.println("Se elimino: " + catalog);
            else
                System.out.println("- No se elimino: " + catalog);
        }
    }

    @Test
    public void testEdit() throws ServiceException {
        this.inventory = new Inventory("Carros");
        catalogFileService = CatalogFileService.getInstance();
        catalogFileService.setInventory(inventory);

        for(int i = 0 ; i < 5; i++) {
            Catalog catalog = catalogFileService.get(Utility.randomCatalogName());
            if(catalog==null) continue;
            List items = randomItemList();
            catalog.setItems(items);
            if (catalogFileService.edit(catalog))
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
        this.inventory = new Inventory("Carros");
        catalogFileService = CatalogFileService.getInstance();

        System.out.println(catalogFileService.getAll());
        System.out.println(catalogFileService.getAll().size());
        System.out.println(catalogFileService.get("Sedan"));
    }
    @Test
    public void testDeleteAll() throws ServiceException {
        this.inventory = new Inventory("Carros");
        catalogFileService = CatalogFileService.getInstance();

        if (catalogFileService.removeAll())
            System.out.println("Se eliminaron los Catalogos de " + inventory.getName());
        else
            System.out.println("- No se elimino los Catalogos de " + inventory.getName());
    }
}

