package edu.ucr.rp.programacion2.proyecto.server.processes;

import java.io.IOException;
import java.net.Socket;

public interface ProcessRequest {
    void insert(Socket socket) throws IOException, ClassNotFoundException;
    void update(Socket socket)  throws IOException, ClassNotFoundException;
    void read(Socket socket) throws IOException, ClassNotFoundException;
    void readAll(Socket socket) throws IOException, ClassNotFoundException;
    void delete(Socket socket) throws IOException, ClassNotFoundException;
    void deleteAll(Socket socket) throws IOException, ClassNotFoundException;
}
