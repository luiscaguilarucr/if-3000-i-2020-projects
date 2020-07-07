module inventory.system {
    requires javafx.controls;
    requires javafx.base;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires commons.io;
    requires org.controlsfx.controls;
    exports edu.ucr.rp.programacion2.proyecto.domain to com.fasterxml.jackson.databind;
    exports edu.ucr.rp.programacion2.proyecto.util to com.fasterxml.jackson.databind;
    exports edu.ucr.rp.programacion2.proyecto.gui to javafx.graphics;
    exports edu.ucr.rp.programacion2.proyecto.persistance.messages to com.fasterxml.jackson.databind;
    opens edu.ucr.rp.programacion2.proyecto.domain to javafx.base;
    opens edu.ucr.rp.programacion2.proyecto.persistance.messages to com.fasterxml.jackson.databind;
}