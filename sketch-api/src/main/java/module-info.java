module com.example.sketchapi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.terbah.sketch.api.sketchapi to javafx.fxml;
    exports com.terbah.sketch.api.sketchapi;
}