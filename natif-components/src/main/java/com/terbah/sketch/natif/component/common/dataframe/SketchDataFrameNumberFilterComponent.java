package com.terbah.sketch.natif.component.common.dataframe;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.data.common.dataframe.SketchNumericDataFrameFilterModel;
import static com.terbah.sketch.natif.component.data.common.dataframe.SketchNumericDataFrameFilterModel.SketchNumericDataFilter;
import com.terbah.sketch.natif.component.ui.common.dataframe.SketchDataFrameNumberFilterPopup;
import java.util.function.Predicate;

/**
 * @author Dorian TERBAH
 *
 * This component will filter a numeric data frame by rows and by columns.
 *
 * @version 1.0
 */
@ComponentConfiguration(
        namespace = "Common/DataFrame",
        name = "Numeric Dataframe Filter"
)
public class SketchDataFrameNumberFilterComponent implements SketchComponent<DataFrame>
{
    /**
     * The model associated to this component.
     */
    private SketchNumericDataFrameFilterModel model;

    /**
     * Create a new SketchNumericDataFrameFilterComponent.
     */
    public SketchDataFrameNumberFilterComponent()
    {
        this.model = new SketchNumericDataFrameFilterModel();
    }

    @Override
    public DataFrame execute() throws SketchComponentExecuteException
    {
        // filter the data
        DataFrame data = this.model.getData();
        if (data == null)
        {
            throw new SketchComponentExecuteException("There is no data to filter.");
        }

        for (SketchNumericDataFilter filter : this.model.getFilters())
        {
            Predicate<Number> predicate = filter.getPredicate();
            data = data.filter(filter.getHeader(), cell -> predicate.test(cell.asNumber()));
        }

        return data;
    }

    @Override
    public SketchComponent<DataFrame> copy() {
        SketchDataFrameNumberFilterComponent component = new SketchDataFrameNumberFilterComponent();
        component.model = this.model.clone();
        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDataFrameNumberFilterPopup(this.model);
    }

    @MethodInjectable(value = "dataframe")
    public void setData(DataFrame data) {
        this.model.setData(data);
    }
}
