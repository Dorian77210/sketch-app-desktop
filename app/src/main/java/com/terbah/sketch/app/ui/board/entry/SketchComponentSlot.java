package com.terbah.sketch.app.ui.board.entry;

import com.terbah.sketch.api.SketchComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Dorian TERBAH
 *
 * UI that represents a slot in a component's ui.
 *
 * @version 1.0
 */
public class SketchComponentSlot extends Rectangle {

    /**
     * Style when the slot is selected.
     */
    private static final Color SELECTED_COLOR = Color.RED;

    /**
     * Style when the slot is unselected.
     */
    private static final Color UNSELECTED_COLOR = Color.BLACK;

    /**
     * Optional value if the slot is an entry.
     */
    private final String entryName;

    /**
     * Type of the slot.
     */
    private final SketchComponentSlotType slotType;

    /**
     * The associated component.
     */
    private final SketchComponent<?> associatedComponent;

    /**
     * Boolean to know if the selected or not
     */
    private boolean isSelected;

    public SketchComponentSlot(SketchComponentSlotType slotType, SketchComponent<?> associatedComponent) {
        this(slotType, associatedComponent, null);
    }

    public SketchComponentSlot(SketchComponentSlotType slotType, SketchComponent<?> associatedComponent, String entryName) {
        super();
        this.entryName = entryName;
        this.slotType = slotType;
        this.associatedComponent = associatedComponent;
        this.isSelected = false;
        this.updateStyle();
    }

    private void updateStyle() {
        this.setFill(this.isSelected ? SELECTED_COLOR : UNSELECTED_COLOR);
    }

    public String getEntryName() {
        return entryName;
    }

    public SketchComponentSlotType getSlotType() {
        return slotType;
    }

    public SketchComponent<?> getAssociatedComponent() {
        return this.associatedComponent;
    }

    public void select() {
        this.isSelected = true;
        this.updateStyle();
    }

    public void unselect() {
        this.isSelected = false;
        this.updateStyle();
    }
}
