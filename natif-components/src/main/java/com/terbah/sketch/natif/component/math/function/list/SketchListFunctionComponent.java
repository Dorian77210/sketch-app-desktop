package com.terbah.sketch.natif.component.math.function.list;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.dataframe.DataFrameFactory;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.SketchDisplayListPopup;
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
    private Function<Number, Number> function;

    /**
     * Data wrapper of the input values.
     */
    protected SketchDataWrapper<NumberList> dataWrapper;

    /**
     * Constructor of the class SketchListFunctionComponent
     * @param function The function used to compute the final result
     */
    public SketchListFunctionComponent(Function<Number, Number> function) {
        this.function = function;
        this.dataWrapper = SketchDataWrapperFactory.getWrapper(NumberList.class);
    }

    @Override
    public DataFrame execute() throws SketchComponentExecuteException {
        if (!this.dataWrapper.isDataAvailable()) {
            throw new SketchComponentExecuteException("No data available for computing mathematical function");
        }

        NumberList x = this.dataWrapper.getData();
        DataFrame dataframe = DataFrameFactory.emptyDataframe();
        NumberList y = new NumberList();

        x.forEach(nbr -> {
            y.add(this.function.apply(nbr));
        });

        // fill the dataframe
        return dataframe.addNumericColumn("x", x).addNumericColumn("y", y);
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDisplayListPopup(this.dataWrapper.getData());
    }

    @MethodInjectable(value = "inputs")
    public void setData(NumberList data) {
        this.dataWrapper.setData(data);
    }
}
