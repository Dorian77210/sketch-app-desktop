package com.terbah.sketch.app.core.workflow;

import com.terbah.sketch.api.SketchComponent;

/**
 * @author Dorian TERBAH
 * @version 1.0
 * <p>
 * Interface that represents a workflow of sketch components.
 */
public interface SketchComponentWorkflow {

    /**
     * Insert a component inside the workflow
     *
     * @param component The component to insert
     */
    void insertComponent(SketchComponent<?> component);

    /**
     * Execute a component.
     *
     * @return <code>true</code> if the execution is a success, else <code>false</code>.
     */
    boolean execute(SketchComponent<?> component);

    /**
     * Create a link between two components using an entry of the target component.
     *
     * @param from      The source component of the link.
     * @param to        The target component of the link.
     * @param entryName The name of the entry of the target component.
     * @return <code>true</code> if the insertion is a success, <code>false</code>.
     */
    boolean createLinkBetween(SketchComponent<?> from, SketchComponent<?> to, String entryName);


    /**
     * Check if there is a link between two components.
     *
     * @param from      The source component.
     * @param to        The target component.
     * @param entryName The name of the entry of the target component.
     * @return <code>true</code> if there is link, else <code>false</code>.
     */
    boolean existsLinkBetween(SketchComponent<?> from, SketchComponent<?> to, String entryName);
}
