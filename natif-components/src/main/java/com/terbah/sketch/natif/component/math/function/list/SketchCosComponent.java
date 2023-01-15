package com.terbah.sketch.natif.component.math.function.list;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.type.NumberList;

import java.util.function.Function;

public class SketchCosComponent extends SketchListFunctionComponent {


    /**
     * Constructor of the class SketchListFunctionComponent
     *
     * @param function The function used to compute the final result
     */
    public SketchCosComponent(Function<NumberList, DataFrame> function) {
        super(function);
    }


    @Override
    public SketchComponent<DataFrame> copy() {
        return null;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return null;
    }
}
