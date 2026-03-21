module com.example.ultssetup {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.ultssetup to javafx.fxml;
    exports com.example.ultssetup;
}