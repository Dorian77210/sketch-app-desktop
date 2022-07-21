package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;

/**
 * @author Dorian TERBAH
 *
 * This interface will permit for the app to create components thanks to
 * a component class.
 *
 * @since 1.0
 */
interface SketchComponentFactory {

    /**
     * Create a component thanks to this class.
     * @param componentClass The class associated to the component.
     * @return The created component, or null if there is no default constructor.
     */
    SketchComponent<?> createComponent(Class<? extends SketchComponent<?>> componentClass);
}
