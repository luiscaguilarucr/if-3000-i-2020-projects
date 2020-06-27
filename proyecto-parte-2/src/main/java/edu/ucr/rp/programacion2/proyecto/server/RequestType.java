package edu.ucr.rp.programacion2.proyecto.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RequestType {
    START("START"),
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
