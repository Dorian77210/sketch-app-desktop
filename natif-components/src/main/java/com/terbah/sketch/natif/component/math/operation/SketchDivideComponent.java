package com.terbah.sketch.natif.component.math.operation;

import com.terbah.sketch.api.annotation.ComponentConfiguration;

/**
 * @author Dorian TERBAH
 *
 * Class used for the divide operation between two numbers
 *
 * @version 1.0
 */
@ComponentConfiguration(
        namespace = "Math/Operation",
        name = "Divide numbers"
)
public class SketchDivideComponent extends SketchOperationComponent {

    /**
     * Constructor for the class SketchOperationComponent
     */
    public SketchDivideComponent() {
        super((a, b) -> a.doubleValue() / b.doubleValue());
    }
}
