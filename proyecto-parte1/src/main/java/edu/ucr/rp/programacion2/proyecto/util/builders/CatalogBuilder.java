package edu.ucr.rp.programacion2.proyecto.util.builders;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.util.inventorycontrol.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogBuilder {
    private Configuration config;
    private String name;
    private List<Map> items;
    private List<String> schema;

    public CatalogBuilder setConfig(Configuration config) {
        this.config = config;
        return this;
    }

    public CatalogBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CatalogBuilder withItems(List<Map> items) {
        this.items = items;
        return this;
    }

    public CatalogBuilder withSchema(List<String> schema) {
        this.schema = schema;
        return this;
    }

    public Catalog build(){
        if(config == null){
            config = new Configuration(-1);
        }else if(config.getId()==0)
            config.setId(-1);
        if(items == null){
            items = new ArrayList<Map>();
        }
        return new Catalog(config, name,
                           items,
                           schema);
    }
}
