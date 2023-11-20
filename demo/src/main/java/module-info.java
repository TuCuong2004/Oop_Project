module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires jlayer;
    requires freetts;

    opens Run to javafx.fxml;
    exports Run;
}