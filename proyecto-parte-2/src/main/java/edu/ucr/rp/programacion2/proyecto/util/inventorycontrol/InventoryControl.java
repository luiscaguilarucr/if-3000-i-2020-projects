package edu.ucr.rp.programacion2.proyecto.util.inventorycontrol;

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

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryControl that = (InventoryControl) o;
        return Objects.equals(inventoryName, that.inventoryName) &&
                Objects.equals(catalogName, that.catalogName);
    }

    @Override
    public String toString() {
        return "InventoryControl{" +
                "inventoryName='" + inventoryName + '\'' +
                ", catalogName='" + catalogName + '\'' +
                '}';
    }
}
