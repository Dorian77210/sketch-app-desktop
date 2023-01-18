package com.terbah.sketch.natif.component.math.function.input;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Component that compute a exp on a single input.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        name = "Exponential on single input",
        namespace = "Math/Input/Function"
)
public class SketchExpComponent extends SketchInputFunctionComponent {

    public SketchExpComponent() {
        super(a -> Math.exp(a.doubleValue()));
    }

    @Override
    public SketchComponent<Number> copy() {
        SketchCosComponent component = new SketchCosComponent();
        component.dataWrapper.setData(this.dataWrapper.getData());
        return component;
    }
}
