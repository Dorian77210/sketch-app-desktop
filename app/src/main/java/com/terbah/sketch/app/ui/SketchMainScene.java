package com.terbah.sketch.app.ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
     * Menu bar on the top of the app.
     */
    private SketchMenuBar menuBar;

    /**
     * The main layout of the application
     */
    private BorderPane mainLayout;

    @Autowired
    private void setMenuBar(SketchMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    @PostConstruct
    private void init() {
        this.mainLayout = (BorderPane) this.getRoot();
        this.mainLayout.setTop(this.menuBar);
        System.out.println(this.menuBar);
    }

    public SketchMainScene() {
        super(new BorderPane(), SKETCH_APP_WIDTH, SKETCH_APP_HEIGHT);
    }


}
