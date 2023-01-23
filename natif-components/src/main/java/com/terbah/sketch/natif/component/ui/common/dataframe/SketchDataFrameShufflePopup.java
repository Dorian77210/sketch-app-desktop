package com.terbah.sketch.natif.component.ui.common.dataframe;

import com.jfoenix.controls.JFXButton;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.ui.SketchAlertBuilder;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.common.dataframe.SketchDataFrameShuffleComponent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

/**
 * @author Dorian TERBAH
 *
 * This popup will be used by the component SketchNumericDataFrameShuffleComponent.
 *
 * @since 1.0
 * @see SketchDataFrameShuffleComponent
 */
public class SketchDataFrameShufflePopup extends SketchConfigurationPopup
{
    /**
     * Create a new popup with the owner window.
     *
     * @param dataframeWrapper The dataframe wrapper associated to this popup.
     * @param iterationsWrapper The iterations' wrapper associated to this popup.
     */
    public SketchDataFrameShufflePopup(SketchDataWrapper<DataFrame> dataframeWrapper, SketchDataWrapper<Integer> iterationsWrapper)
    {
        super("Numeric dataframe shuffle");

        VBox mainPane = new VBox();

        if (dataframeWrapper.isDataAvailable())
        {
            mainPane.getChildren().addAll(new SketchDataFrameView(dataframeWrapper.getData(), "Data that will be shuffled"));
            JFXButton finishButton = new JFXButton("Finish");
            finishButton.getStyleClass().add("button-raised");

            GridPane grid = new GridPane();
            Label iterationsLabel = new Label("Iterations :");
            TextField iterationsField = new TextField();
            iterationsField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
            if (iterationsWrapper.isDataAvailable())
            {
                iterationsField.setText(iterationsWrapper.getData().toString());
            }

            grid.add(iterationsLabel, 0, 0);
            grid.add(iterationsField, 1, 0);

            mainPane.getChildren().addAll(grid, finishButton);

            finishButton.setOnAction(event -> {
                String rawData = iterationsField.getText();
                if (rawData.isEmpty())
                {
                    SketchAlertBuilder.builder()
                            .header("Bad input value")
                            .title("The number of iterations is incorrect")
                            .content("The number of iterations is incorrect. Please fill an integer for this value.")
                            .alertType(Alert.AlertType.ERROR)
                            .build().showAndWait();
                }
                else
                {
                    int iterations = Integer.parseInt(rawData);
                    if (iterations < 0)
                    {
                        SketchAlertBuilder.builder()
                                .header("Bad input value")
                                .title("The number of iterations is incorrect")
                                .content("The number of iterations is incorrect. The number of iterations must be positive.")
                                .alertType(Alert.AlertType.ERROR)
                                .build().showAndWait();
                    }
                    else
                    {
                        iterationsWrapper.setData(iterations);
                        this.close();
                    }
                }
            });
        }

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(SketchDataFrameShuffleComponent.class.getResource("/css/style.css").toExternalForm());
        this.setScene(scene);
    }
}
