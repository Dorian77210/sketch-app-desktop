package com.terbah.sketch.app.ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Service;

/**
 * @author Dorian Terbah
 * @version 1.0
 * <p>
 * This ui class represents the main scene of the application
 */
@Service
public class SketchMainScene extends Scene {
    /**
     * Constant that represents the width of the application
     */
    public static final int SKETCH_APP_WIDTH = 1000;

    /**
     * Constant that represents the height of the application
     */
    public static final int SKETCH_APP_HEIGHT = 800;


    /**
     * The main layout of the application
     */
    private final BorderPane mainLayout;

    /**
     * Default constructor of the class <code>SketchMainScene</code>
     */
    public SketchMainScene() {
        super(new BorderPane(), SKETCH_APP_WIDTH, SKETCH_APP_HEIGHT);
        this.mainLayout = (BorderPane) this.getRoot();
    }
}
