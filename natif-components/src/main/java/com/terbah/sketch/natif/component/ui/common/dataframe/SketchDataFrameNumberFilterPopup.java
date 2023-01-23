package com.terbah.sketch.natif.component.ui.common.dataframe;

import com.jfoenix.controls.JFXButton;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.dataframe.DataFrameColumn;
import com.terbah.sketch.api.data.util.SketchComparisonOperator;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.common.dataframe.SketchDataFrameNumberFilterComponent;
import com.terbah.sketch.natif.component.data.common.dataframe.SketchNumericDataFrameFilterModel;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.terbah.sketch.natif.component.data.common.dataframe.SketchNumericDataFrameFilterModel.SketchNumericDataFilter;

/**
 * @author Dorian TERBAH
 *
 * Popup used to filter a dataframe.
 *
 * @since 1.0
 */
public class SketchDataFrameNumberFilterPopup extends SketchConfigurationPopup
{
    /**
     * The model associated to this popup.
     */
    private final SketchNumericDataFrameFilterModel filterModel;

    /**
     * The main pane of this popup.
     */
    private final VBox filterBox;

    /**
     * Create a new popup with the owner window and the model associated to this popup.
     *
     * @param filterModel The model associated to this popup.
     */
    public SketchDataFrameNumberFilterPopup(SketchNumericDataFrameFilterModel filterModel)
    {
        super("Numeric Dataframe Filter");
        this.filterModel = filterModel;
        VBox mainPane = new VBox();
        VBox.setVgrow(mainPane, Priority.ALWAYS);

        this.filterBox = new VBox();

        DataFrame dataframe = this.filterModel.getData();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(new SketchDataFrameView(dataframe, "Data to filter"));
        mainPane.getChildren().add(scrollPane);

        mainPane.getChildren().add(this.filterBox);

        if (dataframe != null && !dataframe.isEmpty())
        {
            List<SketchNumericDataFilter> filters = this.filterModel.getFilters();
            if (filters.isEmpty())
            {
                // add the view to filter to data.
                GridPane box = this.createFilterBox();
                this.filterBox.getChildren().add(box);
            }
            else
            {
                // add the existing filters.
                for (SketchNumericDataFilter filter : filters)
                {
                    GridPane box = this.createFilterBox(filter);
                    this.filterBox.getChildren().add(box);
                }
            }

            JFXButton finishButton = new JFXButton("Finish");
            finishButton.getStyleClass().add("button-raised");

            finishButton.setOnAction(event -> {
                // close the popup
                this.close();
            });

            JFXButton addFilterButton = new JFXButton("Add filter");
            addFilterButton.getStyleClass().add("button-raised");

            addFilterButton.setOnAction(event -> {
                this.filterBox.getChildren().add(this.createFilterBox());
            });

            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            HBox buttonBox = new HBox();

            buttonBox.getChildren().addAll(finishButton, spacer, addFilterButton);
            mainPane.getChildren().add(buttonBox);
        }

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(SketchDataFrameNumberFilterComponent.class.getResource("/css/style.css").toExternalForm());

        this.setScene(scene);
    }

    /**
     * @return A filter box with a default data filter.
     */
    private GridPane createFilterBox()
    {
        // Create a new SketchNumericDataFrameFilterModel
        SketchNumericDataFilter dataFilter = new SketchNumericDataFilter();
        dataFilter.setValueToFilter(0.0);
        dataFilter.setHeader(this.filterModel.getData().getHeaders().get(0));
        dataFilter.setComparisonOperator(SketchComparisonOperator.getOperators().get(0));
        this.filterModel.addFilter(dataFilter);
        return this.createFilterBox(dataFilter);
    }

    /**
     *
     * @param filter The associated filter to the filter box.
     * @return A filter box with the associated data filter.
     */
    private GridPane createFilterBox(final SketchNumericDataFilter filter)
    {
        GridPane grid = new GridPane();
        grid.setHgap(5.0);

        // header section
        Label headerLabel = new Label("Header to filter :");
        ComboBox<String> headerComboBox = new ComboBox<>();

        List<String> numericHeaders = new ArrayList<>();
        for (String header : this.filterModel.getData().getHeaders())
        {
            DataFrameColumn column = this.filterModel.getData().select(header).get();
            if (column.isNumericColumn())
            {
                numericHeaders.add(header);
            }
        }

        headerComboBox.setItems(FXCollections.observableList(numericHeaders));
        headerComboBox.getSelectionModel().select(filter.getHeader());
        grid.add(headerLabel, 0, 0);
        grid.add(headerComboBox, 1, 0);

        headerComboBox.setOnAction(event -> {
            String header = headerComboBox.getValue();
            filter.setHeader(header);
        });

        // operator section
        Label operatorLabel = new Label("Operator :");
        ComboBox<SketchComparisonOperator> operatorComboBox = new ComboBox<>();
        operatorComboBox.setItems(FXCollections.observableList(SketchComparisonOperator.getOperators()));
        operatorComboBox.getSelectionModel().select(filter.getComparisonOperator());
        grid.add(operatorLabel, 2, 0);
        grid.add(operatorComboBox, 3, 0);
        filter.setComparisonOperator(operatorComboBox.getValue());

        operatorComboBox.setOnAction(event -> {
            SketchComparisonOperator operator = operatorComboBox.getValue();
            filter.setComparisonOperator(operator);
        });

        // value section
        Label valueLabel = new Label("Value to filter :");
        TextField valueField = new TextField("0.0");
        valueField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        grid.add(valueLabel, 4, 0);
        grid.add(valueField, 5, 0);
        valueField.setText(filter.getValueToFilter().toString());

        valueField.setOnKeyTyped(event -> {
            String data = valueField.getText();
            if (!data.isEmpty())
            {
                try
                {
                    filter.setValueToFilter(Double.parseDouble(data));
                }
                catch (Exception e)
                {

                }
            }
        });

        GlyphFont font = GlyphFontRegistry.font("FontAwesome");
        Glyph deleteIcon = font.create(FontAwesome.Glyph.TRASH);
        deleteIcon.setColor(Color.RED);
        JFXButton deleteButton = new JFXButton("", deleteIcon);
        deleteButton.setOnAction(event -> {
            // delete the entry on the filters
            this.filterModel.getFilters().remove(filter);
            this.filterBox.getChildren().remove(grid);
        });

        grid.add(deleteButton, 6, 0);

        return grid;
    }
}
