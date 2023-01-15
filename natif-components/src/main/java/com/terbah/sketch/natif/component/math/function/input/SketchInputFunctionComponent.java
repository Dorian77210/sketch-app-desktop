package com.terbah.sketch.natif.component.math.function.input;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.SketchNumberComponentPopup;

import java.util.function.Function;

/**
 * @author Dorian TERBAH
 *
 * Component used to compute a mathematical function on a single input.
 *
 * @version 1.0
 */
public abstract class SketchInputFunctionComponent implements SketchComponent<Number> {

    /**
     * Function used to compute the final result.
     */
    private Function<Number, Number> function;

    /**
     * Data wrapper of the input
     */
    protected SketchDataWrapper<Number> dataWrapper;

    /**
     * Constructor of the class SketchInputFunctionComponent
     * @param function The function used to compute the final result
     */
    public SketchInputFunctionComponent(Function<Number, Number> function) {
        this.function = function;
        this.dataWrapper = SketchDataWrapperFactory.getWrapper(Number.class);
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchNumberComponentPopup(this.dataWrapper);
    }

    @Override
    public Number execute() throws SketchComponentExecuteException {
        if (!this.dataWrapper.isDataAvailable()) {
            throw new SketchComponentExecuteException("No data available for computing mathematical function");
        }

        return this.function.apply(this.dataWrapper.getData());
    }

    @MethodInjectable(value = "input")
    public void setData(Number data) {
        this.dataWrapper.setData(data);
    }
}
