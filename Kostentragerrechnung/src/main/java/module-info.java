module com.prog.kostentragerrechnung {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires atlantafx.base;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.prog.kostentragerrechnung to javafx.fxml;
    exports com.prog.kostentragerrechnung;
    exports com.prog.kostentragerrechnung.tables;
    opens com.prog.kostentragerrechnung.tables to javafx.fxml;
    exports com.prog.kostentragerrechnung.controller;
    opens com.prog.kostentragerrechnung.controller to javafx.fxml;
}