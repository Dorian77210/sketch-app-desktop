package com.terbah.sketch.natif.component.math;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.data.math.SketchSequenceData;
import com.terbah.sketch.natif.component.ui.math.SketchSequenceComponentPopup;
import com.terbah.sketch.natif.type.NumberList;

/**
 * @author Dorian TERBAH
 *
 * Component that will compute a number sequence between an interval of two
 * values.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        name = "Number Sequence",
        namespace = "Common"
)
public class SketchSequenceComponent implements SketchComponent<NumberList> {

    /**
     * Data of the component.
     */
    private final SketchSequenceData data;

    public SketchSequenceComponent() {
        this.data = new SketchSequenceData();
    }
    @MethodInjectable(value = "begin", order = 1)
    public void setBegin(Number begin) {
        this.data.setStart(begin);
    }

    @MethodInjectable(value = "end", order = 2)
    public void setEnd(Number end) {
        this.data.setEnd(end);
    }

    @Override
    public NumberList execute() throws SketchComponentExecuteException {
        if (!this.data.isRangeDefined())
        {
            throw new SketchComponentExecuteException("The sequence component must be initialized before to be executed.");
        }
        else if (!this.data.isValidStep())
        {
            throw new SketchComponentExecuteException("The step is not correctly defined.");
        }

        NumberList list = new NumberList();
        double start = this.data.getStart().doubleValue();
        double end = this.data.getEnd().doubleValue();
        double step = this.data.getStep().doubleValue();
        double value = start;

        while ((step > 0.0 ? value < end : value > end)) {
            list.add(value);
            value += step;
        }

        return list;
    }

    @Override
    public SketchComponent<NumberList> copy() {
        SketchSequenceComponent component = new SketchSequenceComponent();
        component.data.setStart(this.data.getStart());
        component.data.setEnd(this.data.getEnd());
        component.data.setStep(this.data.getStep());
        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchSequenceComponentPopup(this.data);
    }
}
