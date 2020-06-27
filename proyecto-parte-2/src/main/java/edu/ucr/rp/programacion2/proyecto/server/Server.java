package edu.ucr.rp.programacion2.proyecto.server;

import edu.ucr.rp.programacion2.proyecto.server.messages.Request;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.receive;

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

                Request request = receive(Request.class, socket); // Select the request type.
                System.out.println("Message Recibido: " + jsonUtil.asJson(request));//
                if (request.getType().equals("INSERT_CATALOG")) {
                    request = insertCatalog(socket);
                }
                if (request.getType().equals("CLOSE")) {
                    socket.close();
                }

            }
        } catch (IOException | ClassNotFoundException e) {
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