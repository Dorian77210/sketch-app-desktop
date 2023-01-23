package com.terbah.sketch.natif.component.common.dataframe;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.dataframe.SketchDataFrameShufflePopup;

/**
 * @author Dorian TERBAH
 *
 * Component that have to shuffle a given dataframe with a given number of iterations.
 *
 * @since 1.0
 */

@ComponentConfiguration(
        namespace = "Common/DataFrame",
        name = "DataFrame Shuffle"
)
public class SketchDataFrameShuffleComponent implements SketchComponent<DataFrame>
{
    /**
     * Data wrapper for the dataframe.
     */
    private final SketchDataWrapper<DataFrame> dataframeWrapper;

    /**
     * Data wrapper for the number of iterations.
     */
    private final SketchDataWrapper<Integer> iterationsWrapper;

    /**
     * Create a new SketchNumericDataFrameShuffleComponent
     */
    public SketchDataFrameShuffleComponent()
    {
        this.iterationsWrapper = SketchDataWrapperFactory.getWrapper(Integer.class);
        this.dataframeWrapper = SketchDataWrapperFactory.getWrapper(DataFrame.class);
    }
    @Override
    public DataFrame execute() throws SketchComponentExecuteException
    {
        if (!this.dataframeWrapper.isDataAvailable())
        {
            throw new SketchComponentExecuteException("There is no data to shuffle.");
        }

        if (!this.iterationsWrapper.isDataAvailable())
        {
            throw new SketchComponentExecuteException("There is no iterations for the shuffling.");
        }

        DataFrame dataframe = this.dataframeWrapper.getData();
        return dataframe.shuffle(this.iterationsWrapper.getData());
    }

    @Override
    public SketchComponent<DataFrame> copy() {
        SketchDataFrameShuffleComponent component = new SketchDataFrameShuffleComponent();
        if (this.dataframeWrapper.isDataAvailable()) {
            component.dataframeWrapper.setData(this.dataframeWrapper.getData().copy());
        }

        if (this.iterationsWrapper.isDataAvailable()) {
            component.iterationsWrapper.setData(this.iterationsWrapper.getData());
        }

        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDataFrameShufflePopup(this.dataframeWrapper, this.iterationsWrapper);
    }

    @MethodInjectable(value = "dataframe")
    public void setData(DataFrame data) {
        this.dataframeWrapper.setData(data);
    }
}
