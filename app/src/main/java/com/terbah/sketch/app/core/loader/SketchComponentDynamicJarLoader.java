package com.terbah.sketch.app.core.loader;

/**
 * @author Dorian TERBAH
 *
 * This interface defines the behavior of a jar loader.
 *
 * @since 1.0
 */
public interface SketchComponentDynamicJarLoader {

    /**
     * Load the components in the extern jars.
     */
    void loadComponents();
}
