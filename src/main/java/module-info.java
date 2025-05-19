module ru.nsu.db_proj {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    exports ru.nsu.db_proj.client;
    exports ru.nsu.db_proj;
    opens ru.nsu.db_proj.client to javafx.fxml;
}