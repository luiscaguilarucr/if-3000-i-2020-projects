package edu.ucr.rp.programacion2.proyecto.server.messages;


public class Request {
    private Enum<RequestType> type ;

    public Enum<RequestType> getType() {
        return type;
    }

    public Request setType(Enum<RequestType> type) {
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
