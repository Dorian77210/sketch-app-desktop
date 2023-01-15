package com.terbah.sketch.natif.component.math.operation;

import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Class used for the sub operation between two numbers
 *
 * @version 1.0
 */
@ComponentConfiguration(
        namespace = "Math/Operation",
        name = "Sub numbers"
)
public class SketchSubComponent extends SketchOperationComponent {

    /**
     * Constructor for the class SketchOperationComponent
     */
    public SketchSubComponent() {
        super((a, b) -> a.doubleValue() - b.doubleValue());
    }
}
