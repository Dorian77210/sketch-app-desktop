package com.terbah.sketch.natif.component.math.operation;

import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Class used for the add operation between two numbers
 *
 * @version 1.0
 */
@ComponentConfiguration(
        namespace = "Math/Operation",
        name = "Add numbers"
)
public class SketchAddComponent extends SketchOperationComponent {

    /**
     * Constructor for the class SketchOperationComponent
     */
    public SketchAddComponent() {
        super((a, b) -> a.doubleValue() + b.doubleValue());
    }
}
