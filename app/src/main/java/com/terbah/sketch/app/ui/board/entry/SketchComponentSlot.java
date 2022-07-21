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
     * Type of the slot.
     */
    private final SketchComponentSlotType slotType;

    public SketchComponentSlot(SketchComponentSlotType slotType) {
        this(slotType, null);
    }

    public SketchComponentSlot(SketchComponentSlotType slotType, String entryName) {
        super();
        this.entryName = entryName;
        this.slotType = slotType;
        this.setStyle("-fx-background-color: black;");
    }

    public String getEntryName() {
        return entryName;
    }

    public SketchComponentSlotType getSlotType() {
        return slotType;
    }
}
