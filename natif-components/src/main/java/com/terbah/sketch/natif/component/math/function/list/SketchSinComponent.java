package com.terbah.sketch.natif.component.math.function.list;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.SketchDisplayListPopup;

/**
 * @author Dorian TERBAH
 *
 * Component that will compute the sinus function on a list of numbers.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        name = "Sinus on list input",
        namespace = "Math/List/Function"
)
public class SketchSinComponent extends SketchListFunctionComponent {

    /**
     * Constructor of the class SketchListFunctionComponent
     */
    public SketchSinComponent() {
        super(a -> Math.sin(a.doubleValue()));
    }


    @Override
    public SketchComponent<DataFrame> copy() {
        SketchSinComponent component = new SketchSinComponent();
        component.dataWrapper.setData(this.dataWrapper.getData().copy());
        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDisplayListPopup(this.dataWrapper.getData());
    }
}
