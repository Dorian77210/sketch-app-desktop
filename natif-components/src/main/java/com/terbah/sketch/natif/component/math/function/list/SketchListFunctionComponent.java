package com.terbah.sketch.natif.component.math.function.list;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.type.NumberList;

import java.util.function.Function;

/**
 * @author Dorian TERBAH
 *
 * Component used to compute a mathematical function on a list of inputs.
 *
 * @version 1.0
 */
public abstract class SketchListFunctionComponent implements SketchComponent<DataFrame> {

    /**
     * Function used to compute the final result.
     */
    private Function<NumberList, DataFrame> function;

    /**
     * Data wrapper of the input values.
     */
    protected SketchDataWrapper<NumberList> dataWrapper;

    /**
     * Constructor of the class SketchListFunctionComponent
     * @param function The function used to compute the final result
     */
    public SketchListFunctionComponent(Function<NumberList, DataFrame> function) {
        this.function = function;
        this.dataWrapper = SketchDataWrapperFactory.getWrapper(NumberList.class);
    }

    @Override
    public DataFrame execute() throws SketchComponentExecuteException {
        if (!this.dataWrapper.isDataAvailable()) {
            throw new SketchComponentExecuteException("No data available for computing mathematical function");
        }

        return this.function.apply(this.dataWrapper.getData());
    }

    @MethodInjectable(value = "inputs")
    public void setData(NumberList data) {
        this.dataWrapper.setData(data);
    }
}
