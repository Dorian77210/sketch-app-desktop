package com.terbah.sketch.natif.component.ui.common.chart;


import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author Dorian TERBAH
 *
 * This class represents a node used in the charts to hover the value.
 * Source code :  https://gist.github.com/jewelsea/4681797
 *
 * @version 1.0
 */
public class SketchChartHoveredNode extends StackPane
{
    /**
     * Create a new node with a string data.
     * @param data The data.
     */
    public SketchChartHoveredNode(String data)
    {
        this.setPrefSize(5, 5);

        final Label label = createDataThresholdLabel(data);

        setOnMouseEntered(mouseEvent -> {
            getChildren().setAll(label);
            toFront();
        });
        setOnMouseExited(mouseEvent -> {
            getChildren().clear();
        });
    }

    /**
     * Create a label with a string data.
     * @param data The data for the label.
     * @return The created label.
     */
    private Label createDataThresholdLabel(String data)
    {
        final Label label = new Label(data);
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        label.setTextFill(Color.FIREBRICK);
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}
