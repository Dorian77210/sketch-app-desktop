package com.terbah.sketch.app.ui.controller.command;

import com.terbah.sketch.api.SketchComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorian TERBAH
 *
 * Clipboard that store information for the commands.
 *
 * @version 1.0
 */
public final class ClipBoard {

    /**
     * Singleton.
     */
    private static final ClipBoard INSTANCE = new ClipBoard();

    private List<SketchComponent<?>> selectedComponents;

    private ClipBoard() {
        this.selectedComponents = new ArrayList<>();
    }

    /**
     * @return The only one instance of the clipboard.
     */
    public static ClipBoard getClipBoard() {
        return INSTANCE;
    }

    public boolean isEmpty() {
        return this.selectedComponents.isEmpty();
    }

    /**
     * Update the list of selected components.
     *
     * @param components The selected components.
     */
    public void setSelectedComponents(List<SketchComponent<?>> components) {
        this.selectedComponents = components;
    }

    public List<SketchComponent<?>> getSelectedComponents() {
        return this.selectedComponents;
    }
}
