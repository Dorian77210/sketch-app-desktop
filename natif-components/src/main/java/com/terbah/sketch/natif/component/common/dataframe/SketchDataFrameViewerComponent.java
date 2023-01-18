package com.terbah.sketch.natif.component.common.dataframe;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.dataframe.SketchDataFrameViewPopup;

/**
 * @author Dorian TERBAH
 *
 * This component will help to visualize a numeric dataframe in a spreadsheet.
 *
 * @version 1.0
 */
@ComponentConfiguration(
        namespace = "Common/DataFrame",
        name = "Dataframe Viewer"
)
public class SketchDataFrameViewerComponent implements SketchComponent<Void>
{
    /**
     * Wrapper for the received data frame.
     */
    private final SketchDataWrapper<DataFrame> wrapper;

    /**
     * Create a new SketchNumericDataFrameViewComponent
     */
    public SketchDataFrameViewerComponent()
    {
        this.wrapper = SketchDataWrapperFactory.getWrapper(DataFrame.class);
    }

    public Void execute() throws SketchComponentExecuteException
    {
        return null;
    }

    @Override
    public SketchComponent<Void> copy() {
        SketchDataFrameViewerComponent component = new SketchDataFrameViewerComponent();
        if (this.wrapper.isDataAvailable()) {
            component.wrapper.setData(this.wrapper.getData().copy());
        }

        return component;
    }

    @MethodInjectable(value = "dataframe")
    public void setData(DataFrame dataframe) {
        this.wrapper.setData(dataframe);
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDataFrameViewPopup(this.wrapper);
    }
}
