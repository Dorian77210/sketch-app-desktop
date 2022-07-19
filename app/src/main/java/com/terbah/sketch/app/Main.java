package com.terbah.sketch.app;

import com.terbah.sketch.app.ui.SketchMainScene;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new SketchMainScene());
        stage.show();
    }
}