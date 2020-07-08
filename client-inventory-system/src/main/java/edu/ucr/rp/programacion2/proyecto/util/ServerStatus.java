package edu.ucr.rp.programacion2.proyecto.util;

import java.io.IOException;

public class ServerStatus {
    private static ServerStatus instance;
    private boolean isConnected;
    private ServerStatusRequest serverStatusRequest;

    public static ServerStatus getInstance(){
        if(instance == null){
            instance = new ServerStatus();
        }
        return instance;
    }

    public boolean isConnected() throws IOException {
        serverStatusRequest = ServerStatusRequest.getInstance();
        return serverStatusRequest.isConnected();
    }

}
