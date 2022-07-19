package com.terbah.sketch.api.exception;

/**
 * @author Dorian TERBAH
 * <p>Exception thrown when there was an error during the execution of a component.
 * @version 1.0
 */
public class SketchComponentExecuteException extends Exception {

    /**
     * Create a new SketchComponentExecuteException.
     *
     * @param message The message of the exception.
     */
    public SketchComponentExecuteException(final String message) {
        super(message);
    }
}
