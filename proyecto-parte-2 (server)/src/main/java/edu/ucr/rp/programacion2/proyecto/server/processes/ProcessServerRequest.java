package edu.ucr.rp.programacion2.proyecto.server.processes;

import edu.ucr.rp.programacion2.proyecto.server.messages.ServerRequest;

import java.io.IOException;
import java.net.Socket;

import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.send;

public class ProcessServerRequest {
    public void establishedConnection(Socket socket) throws IOException {
        // Remove all catalogs.
        ServerRequest serverRequest = new ServerRequest();
        serverRequest.setConnected(true);
        // Send confirmation.
        send(serverRequest, socket);
    }
}
