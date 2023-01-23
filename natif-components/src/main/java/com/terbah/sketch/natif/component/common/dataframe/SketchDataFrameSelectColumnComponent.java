package com.terbah.sketch.natif.component.common.dataframe;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.dataframe.SketchDataFrameSelectColumnPopup;

/**
 * @author Dorian TERBAH
 *
 * This component will be able to select some columns inside d a dataframe.
 *
 * @version 1.0
 */
@ComponentConfiguration(
        namespace = "Common/DataFrame",
        name = "Numeric DF column filter"
)
public class SketchDataFrameSelectColumnComponent implements SketchComponent<DataFrame>
{
    /**
     * The wrapper for the NumericDataFrame.
     */
    private SketchDataFrameSelectColumnPopup.SketchDataFrameSelectColumnModel wrapper;

    /**
     * Create a new SketchNumericDataFrameSelectColumnComponent
     */
    public SketchDataFrameSelectColumnComponent()
    {
        this.wrapper = new SketchDataFrameSelectColumnPopup.SketchDataFrameSelectColumnModel();
    }

    @Override
    public DataFrame execute() throws SketchComponentExecuteException
    {
        DataFrame data = this.wrapper.getDataFrame();
        if (data == null)
        {
            throw new SketchComponentExecuteException("The dataframe is not defined.");
        }

        return data.select(this.wrapper.getSelectedHeaders());
    }

    @Override
    public SketchComponent<DataFrame> copy() {
        SketchDataFrameSelectColumnComponent component = new SketchDataFrameSelectColumnComponent();
        component.wrapper = this.wrapper.clone();
        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDataFrameSelectColumnPopup(this.wrapper);
    }

    @MethodInjectable("dataframe")
    public void setData(DataFrame data) {
        this.wrapper.setDataFrame(data);
    }
}
