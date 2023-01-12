package com.terbah.sketch.natif.component.data.math;

import com.terbah.sketch.natif.component.math.SketchSequenceComponent;

/**
 * @author Dorian TERBAH
 *
 * This class represents the model of a SketchSequenceComponent. The numbers
 * are considered like double values.
 *
 * @since 1.0
 * @see SketchSequenceComponent
 */
public class SketchSequenceData
{
    /**
     * The beginning of the sum.
     */
    private Number start;

    /**
     * The greater limit of the sum.
     */
    private Number end;

    /**
     * The step for the sum.
     */
    private Number step;

    /**
     * Boolean used to check if the start integer is fixed or not.
     * The start is fixed when it is set by a parent component.
     */
    private boolean isStartFixed;

    public SketchSequenceData()
    {
        this.start = null;
        this.end = null;
        this.isStartFixed = false;
        this.step = 1.0; // default value
    }

    /**
     * Update the value for the inferior limit of the sum.
     * @param start The new limit.
     */
    public void setStart(Number start)
    {
        if (!this.isStartFixed)
        {
            this.start = start;
        }
    }

    /**
     * @return The inferior limit of the sum.
     */
    public Number getStart()
    {
        return this.start;
    }

    /**
     * Update the value of the greater limit of the sum.
     * @param end The new limit.
     */
    public void setEnd(Number end)
    {
        this.end = end;
    }

    /**
     * @return The greater limit of the sum.
     */
    public Number getEnd()
    {
        return this.end;
    }

    /**
     * Update the value of the step.
     * @param step The new value for the step.
     */
    public void setStep(final Number step)
    {
        this.step = step;
    }

    /**
     * @return The step.
     */
    public Number getStep()
    {
        return this.step;
    }

    public boolean isValidStep()
    {
        if (this.start.doubleValue() > this.end.doubleValue())
        {
            return (this.step.doubleValue() < 0.0);
        }
        else
        {
            return (this.step.doubleValue() > 0.0);
        }
    }

    /**
     * @return <code>true</code> if the end and the start are defined, else <code>false</code>.
     */
    public boolean isRangeDefined()
    {
        return (this.start != null) && (this.end != null);
    }
    /**
     * Set the start fixed or not.
     * @param isStartFixed The new value.
     */
    public void setStartFixed(boolean isStartFixed)
    {
        this.isStartFixed = isStartFixed;
    }

    /**
     * @return <code>true</code> if the start value is fixed, else <code>false</code>.
     */
    public boolean isStartFixed()
    {
        return this.isStartFixed;
    }
}