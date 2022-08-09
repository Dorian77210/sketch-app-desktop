package com.terbah.sketch.app.ui.controller;

import com.terbah.sketch.app.ui.board.SketchComponentUI;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Dorian TERBAH
 *
 * Class that have to control the drag and drop events for components.
 *
 * @since 1.0
 */
public class SketchComponentDragController implements EventHandler<MouseEvent> {

    /**
     * Delta used for the moving of components.
     */
    private final AtomicReference<Point2D> delta;

    /**
     * Mediator.
     */
    private final SketchBoardControllerMediator mediator;

    /**
     * Constructor
     */
    public SketchComponentDragController(SketchBoardControllerMediator mediator) {
        this.delta = new AtomicReference<>();
        this.mediator = mediator;
    }

    @Override
    public void handle(MouseEvent event) {
        Object source = event.getSource();
        if (source instanceof SketchComponentUI ui) {
            EventType<? extends MouseEvent> eventType = event.getEventType();
            double sceneX = event.getSceneX();
            double sceneY = event.getSceneY();
            MouseButton mouseButton = event.getButton();

            if (eventType.equals(MouseEvent.MOUSE_PRESSED))
            {
                this.proceedMousePressedEvent(ui, sceneX, sceneY);
            }
            else if (mouseButton.equals(MouseButton.PRIMARY)) {
                if (eventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    ui.setCursor(Cursor.HAND);
                }
                else if (eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    this.proceedMouseDraggedEvent(ui, sceneX, sceneY);
                }
                else if (eventType.equals(MouseEvent.MOUSE_ENTERED)) {
                    ui.setCursor(Cursor.HAND);
                }
            }
        }
    }

    /**
     * Proceed a mouse pressed event.
     *
     * @param ui The concerned ui.
     * @param sceneX The x coordinate on the scene.
     * @param sceneY The y coordinate on the scene.
     */
    private void proceedMousePressedEvent(final SketchComponentUI ui, double sceneX, double sceneY)
    {
        ui.setCursor(Cursor.MOVE);
        double layoutX = ui.getLayoutX();
        double layoutY = ui.getLayoutY();
        this.delta.set(new Point2D(layoutX - sceneX, layoutY - sceneY));
    }

    private void proceedMouseDraggedEvent(final SketchComponentUI ui, double sceneX, double sceneY)
    {
        double x = sceneX + this.delta.get().getX();
        double y = sceneY + this.delta.get().getY();
        this.mediator.moveComponent(ui, x, y);
    }
}
