package com.terbah.sketch.natif.component.ui.common;

import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.api.ui.SketchAlertBuilder;
import com.terbah.sketch.natif.component.SketchNumberComponent;
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
 * @see SketchNumberComponent
 */
public class SketchNumberComponentPopup extends SketchConfigurationPopup
{
    /**
     * The data associated to the component which created the popup.
     */
    private final SketchDataWrapper<Number> data;

    /**
     * Title of the error alert.
     */
    private static final String ERROR_ALERT_TITLE = "Bad value field";

    /**
     * Header of the error alert.
     */
    private static final String ERROR_ALERT_HEADER = "Error when processing your input";

    /**
     * Content text of the error alert.
     */
    private static final String ERROR_ALERT_CONTENT_TEXT = "The value of the field must be not empty and must be a number";

    /**
     * Error alert used when the user's input is wrong.
     */
    private static final Alert ERROR_ALERT = SketchAlertBuilder
            .builder()
            .header(ERROR_ALERT_HEADER)
            .content(ERROR_ALERT_CONTENT_TEXT)
            .title(ERROR_ALERT_TITLE)
            .alertType(Alert.AlertType.ERROR)
            .build();

    /**
     * Constructor of the class <code>SketchNumberPopUp</code>.
     * @param data The associated data to the SketchIntegerComponent
     */
    public SketchNumberComponentPopup(final SketchDataWrapper<Number> data)
    {
        super("Number input");
        this.data = data;
        BorderPane pane = new BorderPane();

        GridPane grid = new GridPane();
        grid.setHgap(10.0);

        Label label = new Label("Number :");
        Number value = this.data.getData();
        TextField field = new TextField();
        field.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        if (value != null)
        {
            field.setText(value.toString());
        }

        grid.add(label, 0, 0);
        grid.add(field, 1, 0);

        // configuration of the popup
        Button finishButton = new Button("Finish");
        finishButton.setOnAction(event -> {
            String rawData = field.getText();
            if (!rawData.isEmpty())
            {
                this.data.setData(Double.parseDouble(rawData));
                this.close();
            }
            else
            {
                ERROR_ALERT.showAndWait();
            }
        });

        pane.setCenter(grid);
        pane.setBottom(finishButton);
        this.setScene(new Scene(pane));
    }
}