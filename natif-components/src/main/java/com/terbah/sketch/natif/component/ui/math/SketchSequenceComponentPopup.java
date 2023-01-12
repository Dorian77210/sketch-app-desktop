package com.terbah.sketch.natif.component.ui.math;

import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.api.ui.SketchAlertBuilder;
import com.terbah.sketch.natif.component.data.math.SketchSequenceData;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.DoubleStringConverter;

/**
 * @author Dorian TERBAH
 *
 * @version 1.0
 *
 * Popup used to configure the SketchIntegerComponent.
 */
public class SketchSequenceComponentPopup extends SketchConfigurationPopup
{
    /**
     * Sum of the popup.
     */
    private static final String SKETCH_SUM_POPUP_TITLE = "Sum";

    /**
     * The associated data of the component.
     */
    private final SketchSequenceData data;

    /**
     * Create a new SketchSumPopup with the window owner and the title.
     * @param data The associated data of the component.
     */
    public SketchSequenceComponentPopup(final SketchSequenceData data)
    {
        super(SKETCH_SUM_POPUP_TITLE);
        this.data = data;

        BorderPane pane = new BorderPane();

        GridPane grid = new GridPane();
        Label startLabel = new Label("Start : ");
        Label endLabel = new Label("End : ");
        Label stepLabel = new Label("Step : ");

        TextField startField = new TextField();
        startField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        TextField endField = new TextField();
        endField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        TextField stepField = new TextField();
        stepField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        stepField.setText(this.data.getStep().toString());

        Number endValue = this.data.getEnd();
        Number startValue = this.data.getStart();

        if (endValue != null)
        {
            endField.setText(endValue.toString());
        }

        if (startValue != null)
        {
            startField.setText(startValue.toString());
        }

        if (this.data.isStartFixed())
        {
            startField.setEditable(false);
        }

        grid.setHgap(10.0);
        grid.setVgap(5.0);

        // add the components
        grid.add(startLabel, 0, 0);
        grid.add(startField, 1, 0);
        grid.add(endLabel, 0, 1);
        grid.add(endField, 1, 1);
        grid.add(stepLabel, 0, 2);
        grid.add(stepField, 1, 2);

        pane.setCenter(grid);

        // Finish button
        Button finishButton = new Button("Finish");
        finishButton.setOnAction(event -> {
            // check the start and end values
            String startData = startField.getText();
            String endData = endField.getText();
            String stepData = stepField.getText();

            if (!startData.isEmpty())
            {
                Double start = Double.parseDouble(startData);
                this.data.setStart(start);
            }

            if (!endData.isEmpty())
            {
                Double end = Double.parseDouble(endData);
                this.data.setEnd(end);
            }

            if (!stepData.isEmpty())
            {
                Double step = Double.parseDouble(stepData);
                this.data.setStep(step);
            }

            if (!this.data.isRangeDefined())
            {
                SketchAlertBuilder.builder()
                        .title("Bad inputs values")
                        .header("Error when processing the values")
                        .content("The start and the end must be defined")
                        .alertType(Alert.AlertType.ERROR)
                        .build().showAndWait();
            }

            else if (!this.data.isValidStep())
            {
                SketchAlertBuilder.builder()
                        .title("Bad step value")
                        .header("Error when processing the step")
                        .content("The step must be positive if the start is inferior than the end, else negative")
                        .alertType(Alert.AlertType.ERROR)
                        .build().showAndWait();
            }
            else
            {
                this.close();
            }
        });

        pane.setBottom(finishButton);
        this.setScene(new Scene(pane));
    }
}