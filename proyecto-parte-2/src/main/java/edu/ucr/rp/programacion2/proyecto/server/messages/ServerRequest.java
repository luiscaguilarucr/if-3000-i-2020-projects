package edu.ucr.rp.programacion2.proyecto.server.messages;

public class ServerRequest {
    private boolean isConnected;

    public boolean getConnected() {
        return isConnected;
    }

    public ServerRequest setConnected(boolean isConnected) {
        this.isConnected = isConnected;
        return this;
    }
}
