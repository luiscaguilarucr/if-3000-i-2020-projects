package edu.ucr.rp.programacion2.proyecto.persistance.messages;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestType {
    INSERT_CATALOG(0), UPDATE_CATALOG(1),
    READ_CATALOG(2), READ_ALL_CATALOGS(3),
    DELETE_CATALOG(4), DELETE_ALL_CATALOGS(5),
    INSERT_INVENTORY(6), UPDATE_INVENTORY(7),
    READ_INVENTORY(8), READ_ALL_INVENTORIES(9),
    DELETE_INVENTORY(10), DELETE_ALL_INVENTORIES(11),
    CLOSE(12), SET_INVENTORY(13), SERVER_STATUS(14);

    RequestType(int id) {
        this.id = id;
    }

    @JsonValue
    final int id;

}
