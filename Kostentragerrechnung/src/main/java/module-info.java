module com.prog.kostentragerrechnung {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // UI Libraries
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires atlantafx.base;

    // Lombok (compile-time only)
    requires static lombok;

    // Databases
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires com.h2database;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    // JavaFX controller/model binding
    opens com.prog.kostentragerrechnung to javafx.fxml;
    opens com.prog.kostentragerrechnung.controller to javafx.fxml;
    opens com.prog.kostentragerrechnung.controller.dialog to javafx.fxml;
    opens com.prog.kostentragerrechnung.model to javafx.base;

    // Export packages
    exports com.prog.kostentragerrechnung;
    exports com.prog.kostentragerrechnung.model;
    exports com.prog.kostentragerrechnung.controller;
    exports com.prog.kostentragerrechnung.controller.dialog;
    exports com.prog.kostentragerrechnung.service;
}
