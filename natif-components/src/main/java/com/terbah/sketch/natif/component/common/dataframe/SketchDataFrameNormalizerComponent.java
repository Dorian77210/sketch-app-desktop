package com.terbah.sketch.natif.component.common.dataframe;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.dataframe.SketchDataFrameNormalizerPopup;

/**
 * @author Dorian TERBAH
 *
 * This component will receive a numeric data frame and will normalize it.
 * It will return the normalized dataframe.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        namespace = "Common/DataFrame",
        name = "Dataframe Normalizer"
)
public class SketchDataFrameNormalizerComponent implements SketchComponent<DataFrame>
{
    private final SketchDataWrapper<DataFrame> wrapper;

    /**
     * Create a new SketchNumericDataFrameNormalizeComponent.
     */
    public SketchDataFrameNormalizerComponent()
    {
        this.wrapper = SketchDataWrapperFactory.getWrapper(DataFrame.class);
    }

    @Override
    public DataFrame execute() throws SketchComponentExecuteException
    {
        if (!this.wrapper.isDataAvailable())
        {
            throw new SketchComponentExecuteException("There is no data to normalize.");
        }

        return this.wrapper.getData().normalize();
    }

    @Override
    public SketchComponent<DataFrame> copy() {
        SketchDataFrameNormalizerComponent component = new SketchDataFrameNormalizerComponent();
        if (this.wrapper.isDataAvailable()) {
            component.wrapper.setData(this.wrapper.getData().copy());
        }

        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDataFrameNormalizerPopup(this.wrapper);
    }

    @MethodInjectable(value = "dataframe")
    public void setData(DataFrame data) {
        this.wrapper.setData(data);
    }
}
