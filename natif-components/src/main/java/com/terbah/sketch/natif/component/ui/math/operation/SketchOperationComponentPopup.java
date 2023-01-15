package com.terbah.sketch.natif.component.ui.math.operation;

import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.ui.SketchAlertBuilder;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.DoubleStringConverter;

/**
 * @author Dorian TERBAH
 *
 * Popup used to set two numbers in a popup
 *
 * @version 1.0
 */
public class SketchOperationComponentPopup extends SketchConfigurationPopup {

    /**
     * Title of the error alert.
     */
    private static final String ERROR_ALERT_TITLE = "Bad value fields";

    /**
     * Header of the error alert.
     */
    private static final String ERROR_ALERT_HEADER = "Error when processing your inputs";

    /**
     * Content text of the error alert.
     */
    private static final String ERROR_ALERT_CONTENT_TEXT = "The value of the field(s) must be not empty and must be a number";

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
     * First operand
     */
    private SketchDataWrapper<Number> firstOperand;

    /**
     * Second operand
     */
    private SketchDataWrapper<Number> secondOperand;

    /**
     * Create a new popup with the owner window and a specific title.
     *
     * @param title
     */
    public SketchOperationComponentPopup(String title, final SketchDataWrapper<Number> firstOperand, final SketchDataWrapper<Number> secondOperand) {
        super(title);
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;

        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        grid.setHgap(10.0);

        // First operand
        Label firstOperandLabel = new Label("First operand : ");
        TextField firstOperandField = new TextField();
        firstOperandField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        if (this.firstOperand.isDataAvailable()) {
            firstOperandField.setText(this.firstOperand.getData().toString());
        }

        grid.add(firstOperandLabel, 0, 0);
        grid.add(firstOperandField, 1, 0);

        // Second operand
        Label secondOperandLabel = new Label("Second operand : ");
        TextField secondOperandField = new TextField();
        secondOperandField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        if (this.secondOperand.isDataAvailable()) {
            secondOperandField.setText(this.secondOperand.getData().toString());
        }

        grid.add(secondOperandLabel, 0, 1);
        grid.add(secondOperandField, 1, 1);

        // configuration of the popup
        Button finishButton = new Button("Finish");
        finishButton.setOnAction(event-> {
            String rawFirstOperand = firstOperandField.getText();
            String rawSecondOperand = secondOperandField.getText();

            if (!rawFirstOperand.isEmpty() && !rawSecondOperand.isEmpty()) {
                this.firstOperand.setData(Double.parseDouble(rawFirstOperand));
                this.secondOperand.setData(Double.parseDouble(rawSecondOperand));
                this.close();
            } else {
                ERROR_ALERT.showAndWait();
            }
        });

        pane.setCenter(grid);
        pane.setBottom(finishButton);
        this.setScene(new Scene(pane));
    }
}
