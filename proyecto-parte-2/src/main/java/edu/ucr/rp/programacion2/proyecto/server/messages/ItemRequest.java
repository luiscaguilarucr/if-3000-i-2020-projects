package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;

import java.util.List;
import java.util.Map;

public class ItemRequest {

    // Variables
    private Inventory inventory;
    private Catalog catalog;
    private Map<String, Object> item;
    private List<Map<String, Object>> list;

    // Constructor
    public ItemRequest() {
    }

    public ItemRequest(Inventory inventory, Catalog catalog) {
        this.inventory = inventory;
        this.catalog = catalog;
    }

    // Methods
    public Inventory getInventory() {
        return inventory;
    }

    public ItemRequest setInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public ItemRequest setCatalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public Map<String, Object> getItem() {
        return item;
    }

    public ItemRequest setItem(Map<String, Object> item) {
        this.item = item;
        return this;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public ItemRequest setList(List<Map<String, Object>> list) {
        this.list = list;
        return this;
    }
}
