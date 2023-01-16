package com.terbah.sketch.natif.component.ui.common.chart;

import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.dataframe.DataFrameColumn;
import com.terbah.sketch.api.data.dataframe.DataFrameRow;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import java.util.Optional;

/**
 * @author Dorian TERBAH
 *
 * This popup will dipslay a line chart created with a
 * given numeric dataframe.
 * The legend of the chart will be computed with the
 * names of the two first headers of the dataframe.
 *
 * @since 1.0
 */
public class SketchXYLineChartPopup extends SketchConfigurationPopup
{
    /**
     * Create a new popup with the owner window and a specific title.
     *
     * @param data The data associated to the component that created this popup.
     */
    public SketchXYLineChartPopup(SketchDataWrapper<DataFrame> data) {
        super("Sketch Line Chart");
        BorderPane pane = new BorderPane();

        if (data.isDataAvailable())
        {
            DataFrame dataFrame = data.getData();
            DataFrame dataframe = data.getData();
            Optional<DataFrameColumn> xColumnOpt = dataframe.select("x");
            Optional<DataFrameColumn> yColumnOpt = dataframe.select("y");

            if (xColumnOpt.isEmpty() || yColumnOpt.isEmpty()
                    || !xColumnOpt.get().isNumericColumn() || !yColumnOpt.get().isNumericColumn())
            {
                pane.setCenter(new Label("The chart cannot be computed because the data is invalid. The data have at least two columns"));
            }
            else
            {
                NumberAxis xAxis = new NumberAxis();
                NumberAxis yAxis = new NumberAxis();
                LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
                xAxis.setLabel("x");
                yAxis.setLabel("y");

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName("Line chart");

                for (int i = 0; i < dataframe.getRowsCount(); i++)
                {
                    DataFrameRow row = dataframe.rowAt(i);
                    double x = row.at("x").get().asNumber().doubleValue();
                    double y = row.at("y").get().asNumber().doubleValue();
                    XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);
                    dataPoint.setNode(new SketchChartHoveredNode(String.format("x: %s, y:%s", x, y)));
                    series.getData().add(dataPoint);
                }

                lineChart.getData().add(series);
                pane.setCenter(lineChart);
            }
        }

        this.setScene(new Scene(pane));
    }
}
