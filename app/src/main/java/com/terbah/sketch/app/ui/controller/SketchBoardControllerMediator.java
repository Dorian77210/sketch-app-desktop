package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.app.core.board.SketchBoardManager;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflow;
import com.terbah.sketch.app.ui.board.SketchArrow;
import com.terbah.sketch.app.ui.board.SketchBoard;
import com.terbah.sketch.app.ui.board.SketchComponentUI;
import com.terbah.sketch.app.ui.board.entry.SketchComponentSlot;
import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final SketchComponentWorkflow workflow;

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
     * SKetch arrow controller
     */
    private final SketchArrowController arrowController;

    /**
     * Mouse controller for the components
     */
    private final SketchComponentMouseController mouseController;

    /**
     * Logger.
     */
    private Logger logger;

    /**
     * List of all the associations
     */
    private List<SketchArrow> arrows;

    /**
     * Current selected arrow;
     */
    private SketchArrow currentSelectedArrow;

    /**
     * Constructor
     * @param board The associated sketch board
     * @param manager The associated
     */
    public SketchBoardControllerMediator(SketchBoard board, SketchComponentWorkflow workflow, SketchBoardManager manager) {
        this.board = board;
        this.boardManager = manager;
        this.workflow = workflow;
        this.selectedSlots = new ArrayDeque<>();
        this.uiToComponent = new HashMap<>();
        this.selectedComponents = new ArrayList<>();
        this.arrows = new LinkedList<>();
        this.selectedUIs = new ArrayList<>();
        this.dragController = new SketchComponentDragController(this);
        this.logger = SketchLoggerManager.getLogger(this.getClass());
        this.arrowController = new SketchArrowController(this);
        this.mouseController = new SketchComponentMouseController(this);

        // set the listeners
        this.board.setOnMouseClicked(this::receiveClickEvent);
        this.board.setFocusTraversable(true);

        this.currentSelectedArrow = null;
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
        this.workflow.insertComponent(component);
        SketchComponentUI ui = this.boardManager.createUIFor(component);
        ui.setup(this, component);
        ui.setLayoutX(x);
        ui.setLayoutY(y);
        // drag and drop events.
        ui.setOnMousePressed(this.dragController);
        ui.setOnMouseReleased(this.dragController);
        ui.setOnMouseDragged(this.dragController);
        ui.setOnMouseEntered(this.dragController);
        ui.setOnMouseClicked(this.mouseController);

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
        if (this.currentSelectedArrow != null) {
            this.currentSelectedArrow.setIsSelected(false);
            this.currentSelectedArrow = null;
        }
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
        if (this.selectedSlots.contains(slot)) {
            slot.unselect();
            this.selectedSlots.clear();
            return;
        }

        // add an order for the entries.
        this.selectedSlots.push(slot);
        System.out.println("select");
        slot.select();
        if (this.selectedSlots.size() == 2) {
            System.out.println("created");
            SketchComponentSlot childSlot = this.selectedSlots.pop();
            SketchComponentSlot parentSlot = this.selectedSlots.pop();

            String entryName = childSlot.getEntryName();
            SketchComponent<?> child = childSlot.getAssociatedComponent();
            SketchComponent<?> parent = parentSlot.getAssociatedComponent();
            boolean failedLinkCreation = false;

            if (!this.workflow.createLinkBetween(parent, child, entryName)) {
                this.logger.log(Level.SEVERE, "Error during the creation of link");
                failedLinkCreation = true;
            } else {
                this.logger.log(Level.FINE, "Link created !");
            }

            childSlot.unselect();
            parentSlot.unselect();

            if (!failedLinkCreation) {
                this.createLinkBetween(parentSlot, childSlot);
            }
        }
    }

    /**
     * Create an UI association between two slots.
     *
     * @param source The source of the association.
     * @param destination The destination of the association.
     */
    private void createLinkBetween(SketchComponentSlot source, SketchComponentSlot destination) {
        SketchArrow arrow = SketchArrow.fromSourceAndDestination(source, destination);
        this.board.getChildren().add(arrow);
        arrow.setOnMouseClicked(this.arrowController);
        arrow.setOnMouseEntered(this.arrowController);
        this.arrows.add(arrow);
    }

    public List<SketchComponent<?>> getSelectedComponents() {
        return this.selectedComponents;
    }

    /**
     * Delete the selected UI on the sketch board
     */
    public void deleteSelectedUI() {
        if (this.currentSelectedArrow != null) {
            this.deleteSelectedArrow();
        } else {
            this.deleteSelectedComponents();
        }
    }

    /**
     * Delete the selected components in the app
     */
    private void deleteSelectedComponents() {
        for (SketchComponentUI currentUI : this.selectedUIs) {
            SketchComponent<?> component = this.uiToComponent.get(currentUI);
            this.workflow.deleteComponent(component);
            this.board.getChildren().remove(currentUI);

            // remove links
            List<SketchArrow> componentsLinks = this.arrows.stream().filter(arrow -> {
                return arrow.getDestinationComponent().equals(component) ||
                        arrow.getSourceComponent().equals(component);
            }).toList();
            this.arrows.removeAll(componentsLinks);
            this.board.getChildren().removeAll(componentsLinks);
        }
    }

    /**
     * Delete the selected arrow in the app.
     */
    private void deleteSelectedArrow() {
        // delete the link in the workflow
        SketchComponent<?> destination = this.currentSelectedArrow.getDestinationComponent();
        String entryName = this.currentSelectedArrow.getEntryName();
        this.workflow.removeLink(destination, entryName);
        this.logger.log(Level.FINE, String.format("Delete link with destination={} and entry name={}", destination, entryName));
        this.board.getChildren().remove(this.currentSelectedArrow);
        this.currentSelectedArrow = null;
    }

    public void selectArrow(SketchArrow arrow) {
        if (this.currentSelectedArrow != null) {
            this.currentSelectedArrow.setIsSelected(false);
        }

        this.currentSelectedArrow = arrow;
        this.currentSelectedArrow.setIsSelected(true);
    }


    /**
     * Execute a component.
     * @param component The component to execute
     */
    public void executeComponent(final SketchComponent<?> component) {
        this.workflow.execute(component);
    }

    /**
     * Open the configuration popup for a component.
     * @param ui The ui corresponding to the component.
     */
    public void openConfigurationPopup(final SketchComponentUI ui) {
        SketchComponent<?> component = this.uiToComponent.get(ui);
        SketchConfigurationPopup popup = component.openConfigurationPopup();
        popup.show();
    }
}
