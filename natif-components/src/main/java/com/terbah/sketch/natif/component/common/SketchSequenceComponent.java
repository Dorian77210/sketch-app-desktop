package com.terbah.sketch.natif.component.common;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
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
     * The beginning of the interval.
     */
    private Number begin;

    /**
     * The end of the interval.
     */
    private Number end;

    /**
     * Step of the sequence.
     */
    private Number step;

    @MethodInjectable(value = "begin", order = 1)
    public void setBegin(Number begin) {
        this.begin = begin;
    }

    @MethodInjectable(value = "end", order = 2)
    public void setEnd(Number end) {
        this.end = end;
    }



    @Override
    public NumberList execute() throws SketchComponentExecuteException {
        return new NumberList();
    }

    @Override
    public SketchComponent<NumberList> copy() {
        SketchSequenceComponent component = new SketchSequenceComponent();
        component.begin = this.begin;
        component.end = this.end;
        component.step = this.step;
        return component;
    }
}
