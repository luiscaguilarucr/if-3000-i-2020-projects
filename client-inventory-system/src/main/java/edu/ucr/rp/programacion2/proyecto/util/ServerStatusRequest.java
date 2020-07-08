package edu.ucr.rp.programacion2.proyecto.util;

import edu.ucr.rp.programacion2.proyecto.persistance.messages.Request;
import edu.ucr.rp.programacion2.proyecto.persistance.messages.ServerRequest;

import java.io.IOException;
import java.net.Socket;

import static edu.ucr.rp.programacion2.proyecto.logic.SocketConstants.*;
import static edu.ucr.rp.programacion2.proyecto.persistance.messages.RequestType.*;
import static edu.ucr.rp.programacion2.proyecto.util.RequestProcessUtil.receive;
import static edu.ucr.rp.programacion2.proyecto.util.RequestProcessUtil.send;

public class ServerStatusRequest {
    private Socket socket;
    private static ServerStatusRequest instance;

    public static ServerStatusRequest getInstance(){
        if(instance == null){
            instance = new ServerStatusRequest();
        }
        return instance;
    }

    public boolean isConnected() throws IOException {
        Request request = new Request();
        try {
            // Establish the connection with the server.
            socket = new Socket(HOST, PORT);
            // Create a server status request.
            request.setType(SERVER_STATUS);
//            System.out.println("Se ha enviado una confirmación de establecer conexión.");
            // Send and wait the request.
            send(request, socket);

            // Receives an InventoryListRequest.
//            System.out.println("Esperando respuesta.");
            ServerRequest serverRequest = receive(ServerRequest.class, socket);
            boolean isConnected = serverRequest.getConnected();
//            System.out.println("Está conectado= " + isConnected);
            return isConnected;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    /**
     * Send a close request to the server and close the client socket connection.
     *
     * @throws IOException when the connection failed.
     */
    private void closeConnection() throws IOException {
        if (socket != null) {
            Request closeRequest = new Request();
            closeRequest.setType(CLOSE);
            send(closeRequest, socket);
            socket.close();
        }

    }
}
