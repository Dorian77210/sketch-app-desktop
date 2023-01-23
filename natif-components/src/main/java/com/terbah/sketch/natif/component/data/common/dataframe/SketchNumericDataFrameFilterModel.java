package com.terbah.sketch.natif.component.data.common.dataframe;

import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchComparisonOperator;
import com.terbah.sketch.natif.component.common.dataframe.SketchDataFrameNumberFilterComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Dorian TERBAH
 *
 * Model associated to this component. It will store the associated dataframe and the filters.
 *
 * @since 1.0
 */
public class SketchNumericDataFrameFilterModel
{
    /**
     * The dataframe associated to the component.
     */
    private DataFrame data;

    /**
     * The filters to apply.
     */
    private List<SketchNumericDataFilter> filters;

    /**
     * @author Dorian TERBAH
     *
     * Data structure that store an operator, a column header and a value.
     *
     *  @since 1.0
     */

    public static class SketchNumericDataFilter
    {
        /**
         * The associated header.
         */
        private String header;

        /**
         * The value to filter.
         */
        private Number valueToFilter;

        /**
         * The comparison operator.
         */
        private SketchComparisonOperator comparisonOperator;

        /**
         * Default constructor of the SketchNumericDataFilter.
         */
        public SketchNumericDataFilter()
        {
            this.valueToFilter = null;
            this.header = null;
            this.comparisonOperator = null;
        }

        /**
         * Create a new SketchNumericDataFilter.
         * @param header The associated header.
         * @param operator The associated comparison operator.
         * @param valueToFilter The value to filter.
         */
        public SketchNumericDataFilter(String header, SketchComparisonOperator operator, Number valueToFilter)
        {
            this.header = header;
            this.comparisonOperator = operator;
            this.valueToFilter = valueToFilter;
        }

        @Override
        public String toString()
        {
            return "Header = " + this.header +
                    "\nOperator = " + this.comparisonOperator +
                    "\nNumber to filter = " + this.valueToFilter;
        }

        public Predicate<Number> getPredicate()
        {
            if (this.comparisonOperator.equals(SketchComparisonOperator.COMPARISON_OPERATOR_EQUALS))
            {
                return v -> v.doubleValue() == this.valueToFilter.doubleValue();
            }
            else if (this.comparisonOperator.equals(SketchComparisonOperator.COMPARISON_OPERATOR_NOT_EQUALS))
            {
                return v -> v.doubleValue() != this.valueToFilter.doubleValue();
            }
            else if (this.comparisonOperator.equals(SketchComparisonOperator.COMPARISON_OPERATOR_GREATER_THAN))
            {
                return v -> v.doubleValue() > this.valueToFilter.doubleValue();
            }
            else if (this.comparisonOperator.equals(SketchComparisonOperator.COMPARISON_OPERATOR_LESSER_THAN))
            {
                return v -> v.doubleValue() < this.valueToFilter.doubleValue();
            }
            else if (this.comparisonOperator.equals(SketchComparisonOperator.COMPARISON_OPERATOR_GREATER_OR_EQUALS_THAN))
            {
                return v -> v.doubleValue() >= this.valueToFilter.doubleValue();
            }
            else if (this.comparisonOperator.equals(SketchComparisonOperator.COMPARISON_OPERATOR_LESSER_OR_EQUALS_THAN))
            {
                return v -> v.doubleValue() <= this.valueToFilter.doubleValue();
            }

            return null;
        }

        public String getHeader() {
            return header;
        }

        public Number getValueToFilter() {
            return valueToFilter;
        }

        public SketchComparisonOperator getComparisonOperator() {
            return comparisonOperator;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public void setValueToFilter(Number valueToFilter) {
            this.valueToFilter = valueToFilter;
        }

        public void setComparisonOperator(SketchComparisonOperator comparisonOperator) {
            this.comparisonOperator = comparisonOperator;
        }
    }

    /**
     * Create a new model.
     */
    public SketchNumericDataFrameFilterModel()
    {
        this.data = null;
        this.filters = new ArrayList<>();
    }

    /**
     * Update the value for the dataframe.
     * @param data The new value.
     */
    public void setData(DataFrame data)
    {
        this.data = data;
    }

    /**
     * @return The associated dataframe.
     */
    public DataFrame getData()
    {
        return this.data;
    }

    /**
     * Add a new filter.
     * @param dataFilter The new filter to add.
     */
    public void addFilter(SketchNumericDataFilter dataFilter)
    {
        this.filters.add(dataFilter);
    }

    /**
     * @return The filters.
     */
    public List<SketchNumericDataFilter> getFilters()
    {
        return this.filters;
    }

    public SketchNumericDataFrameFilterModel clone() {
        SketchNumericDataFrameFilterModel model = new SketchNumericDataFrameFilterModel();
        if (this.data != null) {
            model.setData(this.data.copy());
        }

        for (SketchNumericDataFilter filter : this.filters) {
            model.addFilter(new SketchNumericDataFilter(filter.header, filter.comparisonOperator, filter.valueToFilter));
        }

        return model;
    }

    /**
     * Remove filter by a given index.
     * @param index The index that represents the filter to remove.
     */
    public void removeFilter(int index)
    {
        if (index < this.filters.size())
        {
            this.filters.remove(index);
        }
    }
}
