package com.terbah.sketch.app.ui.board.entry;
import com.terbah.sketch.api.SketchComponent;
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
     * Style when the slot is selected.
     */
    private static final String SELECTED_STYLE = "-fx-background-color: red;";

    /**
     * Style when the slot is unselected.
     */
    private static final String UNSELECTED_STYLE = "-fx-background-color: black;";

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

        this.setStyle("-fx-background-color: black;");
    }

    private void updateStyle() {
        String style = this.isSelected ? SELECTED_STYLE : UNSELECTED_STYLE;
        this.setStyle(style);
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
