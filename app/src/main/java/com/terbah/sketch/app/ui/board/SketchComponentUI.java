package com.terbah.sketch.app.ui.board;

import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * @author Dorian TERBAH
 *
 * Class that represents the ui aspect of a component.
 *
 * @version 1.0
 */
public class SketchComponentUI extends BorderPane {

    /**
     * The preferred height for the ui.
     */
    static final double SKETCH_COMPONENT_UI_HEIGHT = 90.0;

    /**
     * The preferred width for the ui.
     */
    static final double SKETCH_COMPONENT_UI_WIDTH = 120.0;

    /**
     * Constructor
     *
     * @param configuration The configuration of the associated component.
     */
    public SketchComponentUI(SketchComponentConfiguration configuration) {
        super();
        this.setPrefSize(SKETCH_COMPONENT_UI_WIDTH, SKETCH_COMPONENT_UI_HEIGHT);
        Button button = new Button("button");
        this.setCenter(button);
    }

    /**
     * Set up the UI.
     *
     * @param mediator The mediator associated to this ui.
     */
    public void setup(SketchBoardControllerMediator mediator) {

    }
}
