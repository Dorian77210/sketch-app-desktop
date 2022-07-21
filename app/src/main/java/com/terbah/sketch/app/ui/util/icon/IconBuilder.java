package com.terbah.sketch.app.ui.util.icon;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @author Dorian TERBAH
 *
 * Builder that simplify the creation of icon.
 *
 * @version 1.0
 */
public interface IconBuilder {

    /**
     * Get an icon associated to an enumeration.
     *
     * @param icon The enumeration.
     * @return The graphical icon.
     */
    Node getIcon(SketchIcon icon);

    /**
     * Get an icon associated to an enumeration and a color.
     *
     * @param icon The enumeration.
     * @param color The color.
     * @return The graphical icon with the corresponding color.
     */
    Node getIcon(SketchIcon icon, Color color);

    /**
     * @return A new builder.
     */
    static IconBuilder builder() {
        return new DefaultIconBuilder();
    }
}
