package com.terbah.sketch.app.core.config.validator;

import com.terbah.sketch.api.SketchComponent;

/**
 * @author Dorian TERBAH
 *
 * This interface must be implemented to validate something
 * presents in a component (annotation, methods, ...)
 *
 * @see 1.0
 */
public interface SketchComponentValidator {

    /**
     * @param componentType A component class.
     * @return <code>true</code> if the class is valid, otherwise <code>false</code>.
     */
    boolean validate(Class<? extends SketchComponent<?>> componentType);
}
