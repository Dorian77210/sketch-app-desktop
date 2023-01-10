package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.app.ui.board.SketchArrow;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class SketchArrowController implements EventHandler<MouseEvent> {

    /**
     * Mediator o the app
     */
    private final SketchBoardControllerMediator mediator;

    /**
     * Constructor of the class SketchArrowController
     * @param mediator The mediator of the app
     */
    public SketchArrowController(SketchBoardControllerMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source instanceof SketchArrow arrow) {
            if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
                arrow.setCursor(Cursor.HAND);
            } else if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                this.mediator.selectArrow(arrow);
            }
        }
    }
}
