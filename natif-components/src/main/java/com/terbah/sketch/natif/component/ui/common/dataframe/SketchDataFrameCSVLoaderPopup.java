package com.terbah.sketch.natif.component.ui.common.dataframe;

import com.terbah.sketch.api.ui.SketchAlertBuilder;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.common.dataframe.SketchDataFrameCSVLoaderComponent;
import com.terbah.sketch.natif.component.data.common.dataframe.SketchCSVDataFrameConfiguration;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.controlsfx.control.ToggleSwitch;
import java.io.File;

/**
 * @author Dorian TERBAH
 *
 * This class will configure the SketchNumericDataFrameCSVLoaderComponent.
 *
 * @version 1.0
 * @see SketchDataFrameCSVLoaderComponent
 */
public class SketchDataFrameCSVLoaderPopup extends SketchConfigurationPopup
{
    /**
     * The data associated to the SketchNumericDataFrameCSVLoaderComponent.
     */
    private SketchCSVDataFrameConfiguration data;

    /**
     * Create a new SketchNumericDataFrameCSVLoaderPopup.
     * @param data The associated data.
     */
    public SketchDataFrameCSVLoaderPopup(SketchCSVDataFrameConfiguration data)
    {
        super("CSV Dataframe loader");
        this.data = data;

        // configure the popup
        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();

        Label selectFileLabel = new Label("Select file : ");
        Button selectFileButton = new Button("Open file chooser");

        File csvFile = this.data.getCsvFile();
        if (csvFile != null)
        {
            selectFileButton.setText(csvFile.getName());
        }

        Label hasHeaderLabel = new Label("Headers ?");
        ToggleSwitch hasHeaderToggleSwitch = new ToggleSwitch();
        Boolean hasHeaders = this.data.getHasHeaders();
        if (hasHeaders != null)
        {
            hasHeaderToggleSwitch.setSelected(hasHeaders);
        }

        Label separatorLabel = new Label("Separator :");
        TextField separatorField = new TextField();
        separatorField.setText(this.data.getSeparator());

        Button finishButton = new Button("Finish");

        grid.add(selectFileLabel, 0, 0);
        grid.add(selectFileButton, 1, 0);
        grid.add(hasHeaderLabel, 0, 1);
        grid.add(hasHeaderToggleSwitch, 1, 1);
        grid.add(separatorLabel, 0, 2);
        grid.add(separatorField, 1, 2);

        grid.setHgap(10.0);
        grid.setVgap(5.0);

        selectFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null)
            {
                this.data.setCsvFile(file);
                selectFileButton.setText(file.getName());
                this.sizeToScene();
            }
        });

        finishButton.setOnAction(event -> {
            this.data.setHasHeaders(hasHeaderToggleSwitch.isSelected());

            String separator = separatorField.getText();
            if (separator.isEmpty())
            {
                SketchAlertBuilder.builder()
                        .title("Bad separator value")
                        .header("Separator empty")
                        .content("The separator must not be empty.")
                        .alertType(Alert.AlertType.ERROR)
                        .build().showAndWait();
            }
            else if (this.data.getCsvFile() == null)
            {
                SketchAlertBuilder.builder()
                        .title("Invalid file")
                        .header("Input file null")
                        .content("The file must be selected")
                        .alertType(Alert.AlertType.ERROR)
                        .build().showAndWait();
            }
            else if (!this.data.isValidCSVFile())
            {
                SketchAlertBuilder.builder()
                        .title("CSV Not Valid")
                        .header("The extension of the file is not valid")
                        .content("The extension of the file must be .csv. The file '" + this.data.getCsvFile().getName() + "' is not valid")
                        .alertType(Alert.AlertType.ERROR)
                        .build().showAndWait();
            }
            else
            {
                this.data.setSeparator(separator);
                this.close();
            }
        });

        pane.setCenter(grid);
        pane.setBottom(finishButton);
        this.setScene(new Scene(pane));
    }
}
