package com.terbah.sketch.api;

import com.terbah.sketch.api.exception.SketchComponentExecuteException;

/**
 * @param <T> The type for the execute method. For example,
 *            if a component has the Number type,
 *            it has to return a number
 * @author Dorian TERBAH
 * @version 1.0
 */
public interface SketchComponent<T> {

    /**
     * Execute the component
     *
     * @return The computed result of the result.
     */
    T execute() throws SketchComponentExecuteException;
}
