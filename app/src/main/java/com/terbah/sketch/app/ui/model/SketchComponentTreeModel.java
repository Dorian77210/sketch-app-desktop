package com.terbah.sketch.app.ui.model;

import com.terbah.sketch.api.SketchComponent;

/**
 * @author Dorian TERBAH
 *
 * This interface defines the functionnalities that the tree model
 * containing the components must have.
 *
 * @since 1.0
 */
public interface SketchComponentTreeModel {

    /**
     * Insert a component class inside the tree
     *
     * @param componentClass The component's class.
     */
    void insertComponent(Class<? extends SketchComponent<?>> componentClass);

    /**
     * Check if a component class is presents in the tree
     *
     * @param componentClass The component's class.
     * @return <code>true</code> if the component's class is present, otherwise <code>false</code>.
     */
    boolean containsComponent(Class<? extends SketchComponent<?>> componentClass);

    /**
     * Clear the tree.
     */
    void clear();
}
