module inventory.system {
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires commons.io;
    exports edu.ucr.rp.programacion2.proyecto.domain.logic to  com.fasterxml.jackson.databind;
    exports edu.ucr.rp.programacion2.proyecto.gui to javafx.graphics;
}