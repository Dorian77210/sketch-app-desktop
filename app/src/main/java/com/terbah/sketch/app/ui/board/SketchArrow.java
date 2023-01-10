package com.terbah.sketch.app.ui.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.ui.board.entry.SketchComponentSlot;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * @author Dorian TERBAH
 *
 * @version 1.0
 *
 * UI class used to connect to sketch components.
 *
 * Credit : https://gist.github.com/kn0412/2086581e98a32c8dfa1f69772f14bca4
 */
public class SketchArrow extends Path
{
    /**
     * Default arrow head size.
     */
    private static final double DEFAULT_ARROW_HEAD_SIZE = 10.0;

    /**
     * Source slot of the arrow
     */
    private SketchComponentSlot source;

    /**
     * Destination slot of the arrow
     */
    private SketchComponentSlot destination;

    /**
     * Create a new sketch arrow.
     */
    private SketchArrow(Line line, SketchComponentSlot source, SketchComponentSlot destination)
    {
        super();
        this.source = source;
        this.destination = destination;

        strokeProperty().bind(fillProperty());
        this.setStrokeWidth(4.0);

        this.setFill(Color.BLACK);

        DoubleProperty endY = line.endYProperty();
        DoubleProperty endX = line.endXProperty();
        DoubleProperty startX = line.startXProperty();
        DoubleProperty startY = line.startYProperty();

        MoveTo moveTo = new MoveTo();
        moveTo.xProperty().bind(startX);
        moveTo.yProperty().bind(startY);

        LineTo lineTo = new LineTo();
        lineTo.xProperty().bind(endX);
        lineTo.yProperty().bind(endY);

        this.getElements().add(moveTo);
        this.getElements().add(lineTo);

        // Arrow head
        DoubleBinding angle = Bindings.createDoubleBinding(() ->
                        Math.atan2((endY.get() - startY.get()), (endX.get() - startX.get())) - Math.PI / 2.0,
                startX, startY, endX, endY
        );

        DoubleBinding sin = Bindings.createDoubleBinding(() -> Math.sin(angle.get()), angle);
        DoubleBinding cos = Bindings.createDoubleBinding(() -> Math.cos(angle.get()), angle);

        // point1
        DoubleBinding x1 = Bindings.createDoubleBinding(() ->
                        (- 1.0 / 2.0 * cos.get() + Math.sqrt(3) / 2 * sin.get()) * DEFAULT_ARROW_HEAD_SIZE + endX.get(),
                cos, sin, endX
        );

        DoubleBinding y1 = Bindings.createDoubleBinding(() ->
                        (- 1.0 / 2.0 * sin.get() - Math.sqrt(3) / 2 * cos.get()) * DEFAULT_ARROW_HEAD_SIZE + endY.get(),
                sin, cos, endY
        );

        DoubleBinding x2 = Bindings.createDoubleBinding(() ->
                        (1.0 / 2.0 * cos.get() + Math.sqrt(3) / 2 * sin.get()) * DEFAULT_ARROW_HEAD_SIZE + endX.get(),
                sin, cos, endX
        );

        DoubleBinding y2 = Bindings.createDoubleBinding(() ->
                        (1.0 / 2.0 * sin.get() - Math.sqrt(3) / 2 * cos.get()) * DEFAULT_ARROW_HEAD_SIZE + endY.get(),
                sin, cos, endY
        );

        LineTo l1 = new LineTo(x1.get(), y1.get());
        LineTo l2 = new LineTo(x2.get(), y2.get());
        LineTo l3 = new LineTo(endX.get(), endY.get());

        l1.xProperty().bind(x1);
        l1.yProperty().bind(y1);

        l2.xProperty().bind(x2);
        l2.yProperty().bind(y2);

        l3.xProperty().bind(endX);
        l3.yProperty().bind(endY);

        this.getElements().add(l1);
        this.getElements().add(l2);
        this.getElements().add(l3);
    }

    public void setIsSelected(boolean selected)
    {
        if (selected)
        {
            this.setFill(Color.RED);
        }
        else
        {
            this.setFill(Color.BLACK);
        }
    }

    /**
     * Create a link between a source and a destination slots.
     * @param source The source slot.
     * @param destination The target slot.
     *
     * @return The arrow created.
     */
    public static SketchArrow fromSourceAndDestination(SketchComponentSlot source, SketchComponentSlot destination) {
        Line link = new Line();
        link.startXProperty().bind(source.xProperty());
        link.startYProperty().bind(source.yProperty());
        link.endXProperty().bind(destination.xProperty());
        link.endYProperty().bind(destination.yProperty());
        return new SketchArrow(link, source, destination);
    }

    /**
     * @return The source component associated to this arrow
     */
    public SketchComponent<?> getSourceComponent() {
        return this.source.getAssociatedComponent();
    }

    /**
     * @return The destination component associated to this arrow
     */
    public SketchComponent<?> getDestinationComponent() {
        return this.destination.getAssociatedComponent();
    }
}
