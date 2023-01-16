package com.terbah.sketch.natif.component.common.chart;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.chart.SketchXYLineChartPopup;

/**
 * @author Dorian TERBAH
 *
 * Component used to display line chart
 *
 * @version 1.0
 */

@ComponentConfiguration(
        namespace = "Chart",
        name = "Line chart"
)
public class SketchXYLineChartComponent implements SketchComponent<Void> {

    /**
     * Dataframe used to display the chart
     */
    private SketchDataWrapper<DataFrame> dataWrapper;

    public SketchXYLineChartComponent() {
        this.dataWrapper = SketchDataWrapperFactory.getWrapper(DataFrame.class);
    }

    @Override
    public Void execute() throws SketchComponentExecuteException {
        return null;
    }

    @Override
    public SketchComponent<Void> copy() {
        SketchXYLineChartComponent chart = new SketchXYLineChartComponent();
        if (this.dataWrapper.isDataAvailable()) {
            chart.dataWrapper.setData(this.dataWrapper.getData().copy());
        }
        return chart;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchXYLineChartPopup(this.dataWrapper);
    }

    @MethodInjectable(value = "dataframe")
    public void setData(final DataFrame data) {
        this.dataWrapper.setData(data);
    }
}
