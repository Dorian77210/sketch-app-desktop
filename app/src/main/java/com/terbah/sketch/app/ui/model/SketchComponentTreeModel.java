package com.terbah.sketch.app.ui.model;

import com.terbah.sketch.api.SketchComponent;
import java.util.Set;

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

    /**
     * @return The root of the tree
     */
    SketchComponentTreeItemModel getRoot();

    /**
     * @author Dorian TERBAH
     *
     * Interface that defines what is an item in the tree model.
     *
     * @since 1.0
     */
    interface SketchComponentTreeItemModel {

        /**
         * @return A set with the current node children.
         */
        Set<SketchComponentTreeItemModel> getChildren();

        /**
         * @return The namespace associated to this item.
         */
        String getNamespaceElement();

        /**
         * Update the class associated to this item.
         *
         * @param componentClass The new class.
         */
        void setComponentClass(Class<? extends SketchComponent<?>> componentClass);

        /**
         * Insert a child.
         *
         * @param namespaceElement The namespace element associated to this element.
         * @return The new created child.
         */
        SketchComponentTreeItemModel insertChild(final String namespaceElement);
    }
}
