package com.terbah.sketch.natif.component.common;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentName;
import com.terbah.sketch.api.annotation.ComponentNamespace;
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

@ComponentName("Number Sequence")
@ComponentNamespace("Common")
public class SketchSequenceComponent implements SketchComponent<NumberList> {

    /**
     * The beginning of the interval.
     */
    private Number begin;

    /**
     * The end of the interval.
     */
    private Number end;

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
}
