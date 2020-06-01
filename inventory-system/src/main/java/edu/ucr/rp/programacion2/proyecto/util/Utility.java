package edu.ucr.rp.programacion2.proyecto.util;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {
    public static int random(int bound) {
        return 1 + (int) Math.floor(Math.random() * bound);
    }
    public static Inventory randomInventory(){
        return new Inventory(randomInventoryName());
    }

    public static String randomInventoryName(){
        String[] list = {"Carros", "Barcos", "Aviones", "Herramientas", "Repuestos"};
        return list[random(list.length)-1];
    }
    public static Catalog randomCatalog(){
        return new Catalog(-1, randomCatalogName(), new ArrayList<>(), carsSchema());
    }

    public static String randomCatalogName(){
        String[] list = {"Deportivos", "Todo Terreno", "Clásicos", "SUVs", "Sedan", "Eléctricos", "Pick-ups"};
        return list[random(list.length)-1];
    }
    private static List carsSchema(){
        return new ArrayList<>(Arrays.asList("Marca", "Modelo", "Cant Pasajeros", "Puertas", "Tracción"));
    }
}
