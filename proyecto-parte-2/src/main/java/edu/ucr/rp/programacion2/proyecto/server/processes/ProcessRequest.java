package edu.ucr.rp.programacion2.proyecto.server.processes;

import java.net.Socket;

public interface ProcessRequest {
    void insert(Socket socket);
    void update(Socket socket);
    void read(Socket socket);
    void readAll(Socket socket);
    void delete(Socket socket);
    void deleteAll(Socket socket);

}
