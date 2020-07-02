package edu.ucr.rp.programacion2.proyecto.server.processes;


import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;

import java.io.IOException;
import java.net.Socket;

public class ProcessCatalogRequest implements ProcessRequest {
    private CatalogService catalogService;

    @Override
    public void insert(Socket socket)  throws IOException, ClassNotFoundException {
    }

    @Override
    public void update(Socket socket)  throws IOException, ClassNotFoundException {

    }

    @Override
    public void read(Socket socket)  throws IOException, ClassNotFoundException {

    }

    @Override
    public void readAll(Socket socket)  throws IOException, ClassNotFoundException{

    }

    @Override
    public void delete(Socket socket)  throws IOException, ClassNotFoundException{

    }

    @Override
    public void deleteAll(Socket socket)  throws IOException, ClassNotFoundException{

    }
}
