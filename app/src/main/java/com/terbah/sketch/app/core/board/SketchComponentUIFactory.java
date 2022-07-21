package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.ui.board.SketchComponentUI;

/**
 * @author Dorian TERBAH
 *
 * This interface defines how a sketch component ui must be created.
 *
 * @since 1.0
 */
interface SketchComponentUIFactory {

    /**
     * Create the associated ui of a component.
     *
     * @param component The associated component.
     * @return The created ui.
     */
    SketchComponentUI createUIFor(SketchComponent<?> component);
}
