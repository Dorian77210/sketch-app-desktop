package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.app.core.board.SketchBoardManager;
import com.terbah.sketch.app.ui.controller.command.CommandManager;
import com.terbah.sketch.app.ui.controller.command.CopyCommand;
import com.terbah.sketch.app.ui.controller.command.PasteCommand;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Dorian TERBAH
 *
 * Keyboard controller of the board.
 *
 * @version 1.0
 */
public class SketchKeyboardController implements EventHandler<KeyEvent> {

    /**
     * Key for the copy of component.
     */
    private static final KeyCode KEY_FOR_COPY = KeyCode.C;

    /**
     * Key for the paste of component.
     */
    private static final KeyCode KEY_FOR_PASTE = KeyCode.V;

    /**
     * Key for deleting components.
     */
    private static final KeyCode KEY_FOR_DELETE = KeyCode.DELETE;

    /**
     * Key for deleting components (MACOS)
     */
    private static final KeyCode KEY_FOR_DELETE_MACOS = KeyCode.BACK_SPACE;

    /**
     * Board manager of the app.
     */
    private final SketchBoardManager manager;

    public SketchKeyboardController(SketchBoardManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        if (keyEvent.isControlDown() || keyEvent.isMetaDown()) {
            if (code.equals(KEY_FOR_COPY)) {
                CommandManager.executeCommand(new CopyCommand(this.manager.getCurrentMediator()));
            } else if (code.equals(KEY_FOR_PASTE)) {
                CommandManager.executeCommand(new PasteCommand(this.manager.getCurrentMediator()));
            }
        } else {
            if (code.equals(KEY_FOR_DELETE) || code.equals(KEY_FOR_DELETE_MACOS)) {
                this.manager.getCurrentMediator().deleteSelectedComponents();
            }
        }

        keyEvent.consume();
    }
}
