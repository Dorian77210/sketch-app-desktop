package com.terbah.sketch.natif.component.math.function.input;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Component that compute a cosinus on a single input.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        name = "Cosinus on single input",
        namespace = "Math/Input/Function"
)
public class SketchCosComponent extends SketchInputFunctionComponent {

    public SketchCosComponent() {
        super(a -> Math.cos(a.doubleValue()));
    }

    @Override
    public SketchComponent<Number> copy() {
        SketchCosComponent component = new SketchCosComponent();
        component.dataWrapper.setData(this.dataWrapper.getData());
        return component;
    }
}
