package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private JsonUtil jsonUtil = new JsonUtil();
    private ServerSocket serverSocket = null;

    /**
     * Runs the server.
     * @param port of the server.
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);              // Initialize the Server.
            System.out.println("Esperando conexión");
            while(true) {
                Socket socket = serverSocket.accept();          // Wait for indeterminate client connections.
                System.out.println("Conexión recibida");        // Connection with client established.


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();                           // Close Server connection.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}