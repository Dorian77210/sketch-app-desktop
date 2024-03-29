package com.terbah.sketch.natif.component.math.operation;

import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;

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

    @Override
    public SketchDivideComponent copy() {
        SketchDivideComponent component = new SketchDivideComponent();
        component.firstOperand = SketchDataWrapperFactory.getWrapper(this.firstOperand.getData());
        component.secondOperand = SketchDataWrapperFactory.getWrapper(this.secondOperand.getData());
        return component;
    }
}
