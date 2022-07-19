module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.terbah.app to javafx.fxml;
    exports com.terbah.app;
}