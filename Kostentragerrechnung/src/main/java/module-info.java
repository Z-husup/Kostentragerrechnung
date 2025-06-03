module com.prog.kostentragerrechnung {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires atlantafx.base;
    requires static lombok;
    requires java.sql;

    opens com.prog.kostentragerrechnung to javafx.fxml;
    exports com.prog.kostentragerrechnung;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    exports com.prog.kostentragerrechnung.controller;
    opens com.prog.kostentragerrechnung.controller to javafx.fxml;
    exports com.prog.kostentragerrechnung.controller.dialog;
    opens com.prog.kostentragerrechnung.controller.dialog to javafx.fxml;
    opens com.prog.kostentragerrechnung.model to javafx.base;

}