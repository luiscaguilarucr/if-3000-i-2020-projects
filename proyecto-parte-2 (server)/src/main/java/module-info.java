module inventory.system {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires commons.io;
    exports edu.ucr.rp.programacion2.proyecto.domain to com.fasterxml.jackson.databind;
    exports edu.ucr.rp.programacion2.proyecto.util to com.fasterxml.jackson.databind;
    exports edu.ucr.rp.programacion2.proyecto.server to com.fasterxml.jackson.databind;
    opens edu.ucr.rp.programacion2.proyecto.server.messages;
}