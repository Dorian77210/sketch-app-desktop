package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.app.ui.board.SketchComponentUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * @author Dorian TERBAH
 *
 * @version 1.0
 *
 * This class has to control the mouse interaction with sketch components, like drag and drop, ...
 */
public class SketchComponentMouseController implements EventHandler<MouseEvent> {

    /**
     * Mediator.
     */
    private final SketchBoardControllerMediator mediator;

    public SketchComponentMouseController(final SketchBoardControllerMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source instanceof SketchComponentUI ui) {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) &&
                    mouseEvent.getClickCount() == 2
            ) {
                this.mediator.openConfigurationPopup(ui);
            }
        }
    }
}
