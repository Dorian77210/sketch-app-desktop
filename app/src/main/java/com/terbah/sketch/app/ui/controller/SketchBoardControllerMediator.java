package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.board.SketchBoardManager;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflow;
import com.terbah.sketch.app.ui.board.SketchBoard;
import com.terbah.sketch.app.ui.board.SketchComponentUI;
import com.terbah.sketch.app.ui.board.entry.SketchComponentSlot;
import javafx.scene.input.MouseEvent;

import java.util.*;

/**
 * @author Dorian TERBAH
 *
 * Mediator between all the sketch boards controllers.
 *
 * @version 1.0
 */
public class SketchBoardControllerMediator {

    /**
     * The associated sketch board.
     */
    private final SketchBoard board;

    /**
     * The sketch board manager
     */
    private final SketchBoardManager boardManager;

    /**
     * Associated workflow of the board.
     */
    private final SketchComponentWorkflow worflow;

    /**
     * Selected slots by the user. It will store
     * at most two slots.
     */
    private final Deque<SketchComponentSlot> selectedSlots;

    /**
     * Map an ui with the corresponding component.
     */
    private final Map<SketchComponentUI, SketchComponent<?>> uiToComponent;

    /**
     * List of selected components in the board.
     */
    private final List<SketchComponent<?>> selectedComponents;

    /**
     * List of selected ui.
     */
    private final List<SketchComponentUI> selectedUIs;

    /**
     * Controller for drag and drop event on components.
     */
    private final SketchComponentDragController dragController;

    /**
     * Constructor
     * @param board The associated sketch board
     * @param manager The associated
     */
    public SketchBoardControllerMediator(SketchBoard board, SketchComponentWorkflow workflow, SketchBoardManager manager) {
        this.board = board;
        this.boardManager = manager;
        this.worflow = workflow;
        this.selectedSlots = new ArrayDeque<>();
        this.uiToComponent = new HashMap<>();
        this.selectedComponents = new ArrayList<>();
        this.selectedUIs = new ArrayList<>();
        this.dragController = new SketchComponentDragController(this);

        // set the listeners
        this.board.setOnMouseClicked(this::receiveClickEvent);
        this.board.setFocusTraversable(true);
    }

    /**
     * Method called when the user click on the board
     * @param event The created event
     */
    void receiveClickEvent(MouseEvent event) {
        SketchComponent<?> component = this.boardManager.createComponent();
        if (component != null) {
            this.insertComponent(component, event.getX(), event.getY());
        }
    }

    /**
     * Insert a component.
     * @param component The component to insert.
     */
    public void insertComponent(SketchComponent<?> component) {
        if (component != null) {
            double centerX = (this.board.getLayoutX() + this.board.getWidth()) / 2.0;
            double centerY = (this.board.getLayoutY() + this.board.getHeight()) / 2.0;
            this.insertComponent(component, centerX, centerY);
        }
    }

    private void insertComponent(SketchComponent<?> component, double x, double y) {
        this.worflow.insertComponent(component);
        SketchComponentUI ui = this.boardManager.createUIFor(component);
        ui.setup(this);
        ui.setLayoutX(x);
        ui.setLayoutY(y);
        // drag and drop events.
        ui.setOnMousePressed(this.dragController);
        ui.setOnMouseReleased(this.dragController);
        ui.setOnMouseDragged(this.dragController);
        ui.setOnMouseEntered(this.dragController);

        this.board.getChildren().add(ui);
        this.uiToComponent.put(ui, component);
        this.selectComponent(ui);
    }

    /**
     * Move a component.
     *
     * @param ui The associated ui.
     * @param x The x position.
     * @param y The y position.
     */
    public void moveComponent(SketchComponentUI ui, double x, double y) {
        if (this.isInBounds(ui, x, y)) {
            ui.setLayoutX(x);
            ui.setLayoutY(y);
        }
    }

    private boolean isInBounds(SketchComponentUI ui, double x, double y) {
        return (x > this.board.getLayoutX())
                &&  (x + ui.getWidth() < (this.board.getLayoutX() + this.board.getWidth()))
                &&  (y > this.board.getLayoutY())
                &&  ((y + ui.getHeight()) < (this.board.getLayoutY() + this.board.getHeight()));
    }

    /**
     * Select a component.
     * @param ui The ui associated to the component.
     */
    public void selectComponent(SketchComponentUI ui) {
        this.unselectedComponents();
        ui.setSelected(true);
        this.selectedUIs.add(ui);
        this.selectedComponents.clear();
        this.selectedComponents.add(this.uiToComponent.get(ui));
    }

    /**
     * Unselect all selected components.
     */
    private void unselectedComponents() {
        for (SketchComponentUI currentUI : selectedUIs) {
            currentUI.setSelected(false);
        }

        this.selectedUIs.clear();
    }

    /**
     * Add a selected slot.
     *
     * @param slot The selected to add.
     */
    public void addSelectedSlot(SketchComponentSlot slot) {
        // add an order for the entries.
        System.out.println(slot.getEntryName());
        this.selectedSlots.push(slot);
        if (this.selectedSlots.size() == 2) {
            // try to create link between the two components
        }
    }

    public List<SketchComponent<?>> getSelectedComponents() {
        return this.selectedComponents;
    }

    /**
     * Delete the selected components from the board.
     */
    public void deleteSelectedComponents() {
        for (SketchComponentUI currentUI : this.selectedUIs) {
            SketchComponent<?> component = this.uiToComponent.get(currentUI);
            this.worflow.deleteComponent(component);
            this.board.getChildren().remove(currentUI);
        }
    }
}
