package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;
import java.util.Objects;

public class Catalog {

    private String name;
    private List<Item> items;
    private List<Feature> properties;//TODO revisar properties.

    /**
     * @param name       name of the catalogue
     * @param properties properties that are going to define the items.
     */
    public Catalog(String name, List<Item> items, List<Feature> properties) {//TODO valorar el uso de Item...items
        this.name = name;
        this.items = items;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Feature> getProperties() {
        return properties;
    }

    public void setProperties(List<Feature> properties) {
        this.properties = properties;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item getItem(String name) {
        return items.get(items.indexOf(new Item(name, null)));
    }
    public Boolean containsItem(String name){
        return items.contains(new Item(name, null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(name, catalog.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", items=" + items +
                ", properties=" + properties +
                '}';
    }
}
