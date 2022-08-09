package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.ui.board.SketchBoard;
import com.terbah.sketch.app.ui.board.SketchComponentUI;
import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;

/**
 * @author Dorian Terbah
 *
 * @version 1.0
 *
 * Manage the different sketch board of the application.
 */
public interface SketchBoardManager {

    /**
     * @return The created board.
     */
    SketchBoard createSketchBoard();

    /**
     * Update the current component class selected.
     *
     * @param componentClass The new value.
     */
    void setComponentClassSelected(Class<? extends SketchComponent<?>> componentClass);

    /**
     * The manager will try to create a component, if a component is selected on the tree view.
     * If it is, it will create the component at the position x/y
     */
    SketchComponent<?> createComponent();

    /**
     * Create the ui for a component.
     *
     * @param component The component.
     * @return The created UI.
     */
    SketchComponentUI createUIFor(SketchComponent<?> component);

    /**
     * @return The current mediator.
     */
    SketchBoardControllerMediator getCurrentMediator();
}
