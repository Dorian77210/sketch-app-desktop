package com.terbah.sketch.natif.component.math.function.input;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Component that compute an absolute on a single input.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        name = "Abs on single input",
        namespace = "Math/Input/Function"
)
public class SketchAbsComponent extends SketchInputFunctionComponent {

    public SketchAbsComponent() {
        super(a -> Math.abs(a.doubleValue()));
    }

    @Override
    public SketchComponent<Number> copy() {
        SketchCosComponent component = new SketchCosComponent();
        component.dataWrapper.setData(this.dataWrapper.getData());
        return component;
    }
}
