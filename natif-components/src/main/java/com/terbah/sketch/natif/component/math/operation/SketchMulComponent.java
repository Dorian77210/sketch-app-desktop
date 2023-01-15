package com.terbah.sketch.natif.component.math.operation;

import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;

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

    @Override
    public SketchMulComponent copy() {
        SketchMulComponent component = new SketchMulComponent();
        component.firstOperand = SketchDataWrapperFactory.getWrapper(this.firstOperand.getData());
        component.secondOperand = SketchDataWrapperFactory.getWrapper(this.secondOperand.getData());
        return component;
    }
}
