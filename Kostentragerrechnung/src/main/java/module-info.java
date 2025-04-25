module com.prog.kostentragerrechnung {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.prog.kostentragerrechnung to javafx.fxml;
    exports com.prog.kostentragerrechnung;
    exports com.prog.kostentragerrechnung.model;
    opens com.prog.kostentragerrechnung.model to javafx.fxml;
    exports com.prog.kostentragerrechnung.controller;
    opens com.prog.kostentragerrechnung.controller to javafx.fxml;
    exports com.prog.kostentragerrechnung.repository;
    opens com.prog.kostentragerrechnung.repository to javafx.fxml;
}