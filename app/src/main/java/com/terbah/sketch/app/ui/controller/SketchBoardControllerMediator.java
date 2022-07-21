package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.board.SketchBoardManager;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflow;
import com.terbah.sketch.app.ui.board.SketchBoard;
import com.terbah.sketch.app.ui.board.SketchComponentUI;
import javafx.scene.input.MouseEvent;

/**
 * @author Dorian TERBAH
 *
 * Mediator between all the sketch boards controllers.
 *
 * @version 1.0
 */
public class SketchBoardControllerMediator {

    /**
     * The associated sketch board.
     */
    private final SketchBoard board;

    /**
     * The sketch board manager
     */
    private final SketchBoardManager boardManager;

    /**
     * Associated workflow of the board.
     */
    private final SketchComponentWorkflow worflow;

    /**
     * Constructor
     * @param board The associated sketch board
     * @param manager The associated
     */
    public SketchBoardControllerMediator(SketchBoard board, SketchComponentWorkflow workflow, SketchBoardManager manager) {
        this.board = board;
        this.boardManager = manager;
        this.worflow = workflow;

        // set the listeners
        this.board.setOnMouseClicked(this::receiveClickEvent);
    }

    /**
     * Method called when the user click on the board
     * @param event The created event
     */
    void receiveClickEvent(MouseEvent event) {
        SketchComponent<?> component = this.boardManager.createComponent();
        if (component != null) {
            this.worflow.insertComponent(component);
            SketchComponentUI ui = this.boardManager.createUIFor(component);
            ui.setup(this);

            ui.setLayoutX(event.getX());
            ui.setLayoutY(event.getY());
            this.board.getChildren().add(ui);
        }
    }
}
