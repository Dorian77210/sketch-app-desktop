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
     * @param component The component child insert
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
     * @param parent      The source component of the link.
     * @param child        The target component of the link.
     * @param entryName The name of the entry of the target component.
     * @return <code>true</code> if the insertion is a success, <code>false</code>.
     */
    boolean createLinkBetween(SketchComponent<?> parent, SketchComponent<?> child, String entryName);


    /**
     * Check if there is a link between two components.
     *
     * @param parent      The source component.
     * @param child        The target component.
     * @param entryName The name of the entry of the target component.
     * @return <code>true</code> if there is link, else <code>false</code>.
     */
    boolean existsLinkBetween(SketchComponent<?> parent, SketchComponent<?> child, String entryName);

    /**
     * Remove a link between two components associated by the entry name.
     *
     * @param child     The target component.
     * @param entryName The name of the entry for of the target component.
     */
    void removeLink(SketchComponent<?> child, String entryName);
}
