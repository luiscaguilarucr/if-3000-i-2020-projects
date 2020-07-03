package edu.ucr.rp.programacion2.proyecto.persistance;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;
import edu.ucr.rp.programacion2.proyecto.util.builders.CatalogBuilder;
import edu.ucr.rp.programacion2.proyecto.domain.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogSocketPersistence implements CatalogPersistence {

    private String host;
    private int port;
    private Socket clientSocket;
    private JsonUtil jsonUtil = new JsonUtil();

    public CatalogSocketPersistence(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public boolean insert(Catalog catalog) throws PersistenceException {
        return false;
    }

    @Override
    public boolean update(Catalog catalog) throws PersistenceException {
        return false;
    }

    @Override
    public Catalog read(List<Catalog> key) throws PersistenceException {
        return null;
    }

    @Override
    public List<Catalog> readAll() throws PersistenceException {
        return null;
    }

    @Override
    public boolean delete(Catalog catalog) throws PersistenceException {
        return false;
    }

    @Override
    public boolean deleteAll() throws PersistenceException {
        return false;
    }
}
