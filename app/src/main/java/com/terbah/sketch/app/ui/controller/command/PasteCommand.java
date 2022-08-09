package com.terbah.sketch.app.ui.controller.command;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;

import java.util.List;

/**
 * @author Dorian TERBAH
 *
 * Paste command to paste components.
 *
 * @version 1.0
 */
public class PasteCommand extends Command {

    public PasteCommand(SketchBoardControllerMediator mediator) {
        super(mediator);
    }

    @Override
    public boolean execute() {
        if (ClipBoard.getClipBoard().isEmpty()) {
            return false;
        }

        List<SketchComponent<?>> selectedComponents = ClipBoard.getClipBoard().getSelectedComponents();
        for (SketchComponent<?> selectedComponent : selectedComponents) {
            this.mediator.insertComponent(selectedComponent.copy());
        }

        return true;
    }
}
