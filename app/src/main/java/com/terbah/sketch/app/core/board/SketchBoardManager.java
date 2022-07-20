package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.ui.board.SketchBoard;

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
}
