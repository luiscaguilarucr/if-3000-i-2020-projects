package edu.ucr.rp.programacion2.proyecto.server.messages;

public class Request {
    private RequestType type;

    public RequestType getType() {
        return type;
    }

    public Request setType(RequestType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                '}';
    }
}
