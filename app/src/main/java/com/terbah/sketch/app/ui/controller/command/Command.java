package com.terbah.sketch.app.ui.controller.command;

import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;

/**
 * @author Dorian TERBAH
 *
 * Class that defines a shortcut command.
 *
 * @version 1.0
 */
public abstract class Command {

    /**
     * Mediator of the current board.
     */
    protected SketchBoardControllerMediator mediator;

    protected Command(SketchBoardControllerMediator mediator) {
        this.mediator = mediator;
    }

    public abstract boolean execute();
}
