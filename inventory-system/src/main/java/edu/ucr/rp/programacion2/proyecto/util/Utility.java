package edu.ucr.rp.programacion2.proyecto.util;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Configuration;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.Item;
import edu.ucr.rp.programacion2.proyecto.util.builders.ItemBuilder;

import java.util.*;

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
        return new Catalog(new Configuration(-1), randomCatalogName(), new ArrayList<>(), carsSchema());
    }
    public static String randomCatalogName(){
        String[] list = {"Deportivos", "Todo Terreno", "Clásicos", "SUVs", "Sedan", "Eléctricos", "Pick-ups", "Convertibles", "Híbridos",
        "Vans", "MiniVans", "Autos de Lujo", "Coupé", "Crossover", "Hatchback"};
        return list[random(list.length)-1];
    }
    private static List carsSchema(){
        return new ArrayList<>(Arrays.asList("Modelo", "Cant Pasajeros", "Puertas", "Trasmisión"));

    }

    //  Random Car items  \\
    public static Item randomItem(){
        return new ItemBuilder()
                .setName(randomCarBrand())
                .setFeatures(randomCarFeatures())
                .build();
    }


    public static String randomCarBrand(){
        String[] list = {"Ford", "Audi", "Toyota", "Suzuki", "Land Rover", "Hyundai", "Mitsubishi", "Nissan", "Fiat", "Ferrari", "Lamborghini",
                "Tesla", "Kia", "Honda"};
        return list[random(list.length)-1];
    }

    private static Map<String, Object> randomCarFeatures() {
        List<String> schema = carsSchema();
        Map<String, Object> features = new HashMap<>();

        features.put(schema.get(0), randomModel());
        features.put(schema.get(1), randomSeatsNum());
        features.put(schema.get(2), randomDoorsNum());
        features.put(schema.get(3), randomCarTransmission());
        return features;
    }
    public static String randomModel(){
        return String.valueOf(1965+random(55));
    }
    public static String randomCarTransmission(){
        String[] list = {"Automática", "Manual", "Dual"};
        return list[random(list.length)-1];
    }
    public static String randomSeatsNum(){
        Integer[] list = {2, 4, 5, 7};
        return String.valueOf(list[random(list.length)-1]);
    }
    public static String randomDoorsNum(){
        Integer[] list = {2, 4};
        return String.valueOf(list[random(list.length)-1]);
    }

}
