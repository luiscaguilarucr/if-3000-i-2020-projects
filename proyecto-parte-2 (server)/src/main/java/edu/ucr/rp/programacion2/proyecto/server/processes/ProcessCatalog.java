package edu.ucr.rp.programacion2.proyecto.server.processes;

import java.io.IOException;
import java.net.Socket;

public interface ProcessCatalog extends ProcessRequest{
    void refresh(Socket socket) throws IOException, ClassNotFoundException;
}
