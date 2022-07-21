package com.terbah.sketch.app.ui.board.entry;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author Dorian TERBAH
 *
 * UI that represents a slot in a component's ui.
 *
 * @version 1.0
 */
public class SketchComponentSlot extends Label {

    /**
     * Optional value if the slot is an entry.
     */
    private final String entryName;

    /**
     * The associated type for the slot.
     */
    private final Class<?> associatedType;

    /**
     * Type of the slot.
     */
    private final SketchComponentSlotType slotType;

    public SketchComponentSlot(SketchComponentSlotType slotType, Class<?> type) {
        this(slotType, null, type);
    }

    public SketchComponentSlot(SketchComponentSlotType slotType, String entryName, Class<?> type) {
        super();
        this.entryName = entryName;
        this.slotType = slotType;
        this.associatedType = type;
        this.setStyle("-fx-background-color: black;");
    }

    public String getEntryName() {
        return entryName;
    }

    public SketchComponentSlotType getSlotType() {
        return slotType;
    }
    Class<?> getAssociatedType() {
        return associatedType;
    }
}
