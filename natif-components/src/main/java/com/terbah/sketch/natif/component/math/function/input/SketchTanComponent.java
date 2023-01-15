package com.terbah.sketch.natif.component.math.function.input;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Component that compute a cosinus of a single input.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        name = "Tangent on single input",
        namespace = "Math/Function"
)
public class SketchTanComponent extends SketchInputFunctionComponent {

    public SketchTanComponent() {
        super(a -> Math.tan(a.doubleValue()));
    }

    @Override
    public SketchComponent<Number> copy() {
        SketchCosComponent component = new SketchCosComponent();
        component.dataWrapper.setData(this.dataWrapper.getData());
        return component;
    }
}
