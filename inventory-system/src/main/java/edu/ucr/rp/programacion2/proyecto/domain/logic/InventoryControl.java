package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.Objects;

public class InventoryControl {
    private String inventoryName;
    private String catalogName;

    public InventoryControl(String inventoryName, String catalogName) {
        this.inventoryName = inventoryName;
        this.catalogName = catalogName;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public InventoryControl setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
        return this;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public InventoryControl setCatalogName(String catalogName) {
        this.catalogName = catalogName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryControl that = (InventoryControl) o;
        return Objects.equals(inventoryName, that.inventoryName) &&
                Objects.equals(catalogName, that.catalogName);
    }
}
