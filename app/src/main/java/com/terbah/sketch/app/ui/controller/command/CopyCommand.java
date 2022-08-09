package com.terbah.sketch.app.ui.controller.command;

import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;

public class CopyCommand extends Command {

    public CopyCommand(SketchBoardControllerMediator mediator) {
        super(mediator);
    }

    @Override
    public boolean execute() {
        ClipBoard.getClipBoard().setSelectedComponents(this.mediator.getSelectedComponents());
        return false;
    }
}
