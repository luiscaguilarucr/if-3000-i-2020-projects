package edu.ucr.rp.programacion2.proyecto.server.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RequestType {
    @JsonProperty("START")
    START("START"),
    @JsonProperty("CLOSE")
    CLOSE("CLOSE");

    private String type;

    RequestType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public RequestType setType(String type) {
        this.type = type;
        return this;
    }
}
