package com.terbah.sketch.natif.component.math.function.input;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.SketchDisplayListPopup;

/**
 * @author Dorian TERBAH
 *
 * Component that will compute the log function on a list of numbers.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        name = "Log on list input",
        namespace = "Math/List/Function"
)
public class SketchLogComponent extends SketchListFunctionComponent {

    /**
     * Constructor of the class SketchListFunctionComponent
     */
    public SketchLogComponent() {
        super(a -> Math.log(a.doubleValue()));
    }


    @Override
    public SketchComponent<DataFrame> copy() {
        SketchCosComponent component = new SketchCosComponent();
        component.dataWrapper.setData(this.dataWrapper.getData().copy());
        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDisplayListPopup("Numbers computed with cosinus function", this.dataWrapper.getData());
    }
}
