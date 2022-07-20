package com.terbah.sketch.app;

import com.terbah.sketch.app.ui.SketchMainScene;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        SketchMainScene mainScene = context.getBean(SketchMainScene.class);
        stage.setScene(mainScene);
        stage.show();
    }
}