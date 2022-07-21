package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.board.SketchBoardManager;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflow;
import com.terbah.sketch.app.ui.board.SketchBoard;
import com.terbah.sketch.app.ui.board.SketchComponentUI;
import com.terbah.sketch.app.ui.board.entry.SketchComponentSlot;
import javafx.scene.input.MouseEvent;

import java.util.ArrayDeque;
import java.util.Deque;

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
     * Selected slots by the user. It will store
     * at most two slots.
     */
    private final Deque<SketchComponentSlot> selectedSlots;

    /**
     * Constructor
     * @param board The associated sketch board
     * @param manager The associated
     */
    public SketchBoardControllerMediator(SketchBoard board, SketchComponentWorkflow workflow, SketchBoardManager manager) {
        this.board = board;
        this.boardManager = manager;
        this.worflow = workflow;
        this.selectedSlots = new ArrayDeque<>();

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

    /**
     * Add a selected slot.
     *
     * @param slot The selected to add.
     */
    public void addSelectedSlot(SketchComponentSlot slot) {
        // add an order for the entries.
        System.out.println(slot.getEntryName());
        this.selectedSlots.push(slot);
        if (this.selectedSlots.size() == 2) {
            // try to create link between the two components
        }
    }
}
