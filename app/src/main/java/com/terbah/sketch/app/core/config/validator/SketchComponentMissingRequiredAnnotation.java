package com.terbah.sketch.app.core.config.validator;

/**
 * @author Dorian TERBAH
 *
 * This exception will be used when there is a missing annotation on a
 * component's class.
 *
 * @since 1.0
 */
public class SketchComponentMissingRequiredAnnotation extends Exception {

    /**
     * Constructor
     * @param message The message associated to the exception.
     */
    public SketchComponentMissingRequiredAnnotation(String message) {
        super(message);
    }
}
