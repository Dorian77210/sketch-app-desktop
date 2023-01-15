package com.terbah.sketch.natif.component.math.operation;

import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Class used for the mul operation between two numbers
 *
 * @version 1.0
 */
@ComponentConfiguration(
        namespace = "Math/Operation",
        name = "Multiply numbers"
)
public class SketchMulComponent extends SketchOperationComponent {

    /**
     * Constructor for the class SketchOperationComponent
     */
    public SketchMulComponent() {
        super((a, b) -> a.doubleValue() * b.doubleValue());
    }
}
