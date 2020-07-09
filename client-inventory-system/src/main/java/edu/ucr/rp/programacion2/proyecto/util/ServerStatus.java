package edu.ucr.rp.programacion2.proyecto.util;

import java.io.IOException;

public class ServerStatus {
    private static ServerStatus instance;
    private static ServerStatusRequest serverStatusRequest;

    private ServerStatus(){
        serverStatusRequest = ServerStatusRequest.getInstance();
    }
    public static ServerStatus getInstance(){
        if(instance == null){
            instance = new ServerStatus();
        }
        return instance;
    }

    public static boolean isConnected() {
        try {
            return serverStatusRequest.isConnected();
        } catch (IOException e) {
            return false;
        }
    }

}
