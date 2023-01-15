package com.terbah.sketch.natif.component.math.operation;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.math.operation.SketchOperationComponentPopup;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author Dorian TERBAH
 *
 * Class used for the basic mathematical operations between two numbers
 *
 * @version 1.0
 */
public abstract class SketchOperationComponent implements SketchComponent<Number> {

    /**
     * Function used for computing a result
     */
    private BiFunction<Number, Number, Number> operation;

    /**
     * First operand
     */
    protected SketchDataWrapper<Number> firstOperand;

    /**
     * Second operand
     */
    protected SketchDataWrapper<Number> secondOperand;

    /**
     * Constructor for the class SketchOperationComponent
     * @param operation The operation used to compute a result
     */
    public SketchOperationComponent(BiFunction<Number, Number, Number> operation) {
        Objects.requireNonNull(operation);
        this.operation = operation;

        this.firstOperand = SketchDataWrapperFactory.getWrapper(Number.class);
        this.secondOperand = SketchDataWrapperFactory.getWrapper(Number.class);
    }

    @Override
    public Number execute() throws SketchComponentExecuteException {
        if (!this.firstOperand.isDataAvailable()) {
            throw new SketchComponentExecuteException("Invalid first operand");
        }

        if (!this.secondOperand.isDataAvailable()) {
            throw new SketchComponentExecuteException("Invalid second operand");
        }

        try {
            return this.operation.apply(this.firstOperand.getData(), this.secondOperand.getData());
        } catch (Exception exception) {
            throw new SketchComponentExecuteException(exception.getMessage());
        }
    }

    /**
     * Set the first operand
     * @param operand The first operand
     */
    @MethodInjectable(value = "first operand", order = 0)
    public void setFirstOperand(Number operand) {
        this.firstOperand.setData(operand);
    }

    /**
     * Set the second operand
     * @param operand The second operand
     */
    @MethodInjectable(value = "second operand", order = 1)
    public void setSecondOperand(Number operand) {
        this.secondOperand.setData(operand);
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchOperationComponentPopup("Operand setter", this.firstOperand, this.secondOperand);
    }
}
