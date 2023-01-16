package com.terbah.sketch.natif.component.common;


import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.ui.common.SketchNumberComponentPopup;


@ComponentConfiguration(
        name = "Number input",
        namespace = "Common"
)
public class SketchNumberComponent implements SketchComponent<Number> {

    private SketchDataWrapper<Number> value;

    public SketchNumberComponent() {
        this.value = SketchDataWrapperFactory.getWrapper(0.0);
    }

    @Override
    public Number execute() throws SketchComponentExecuteException {
        return this.value.isDataAvailable() ? this.value.getData() : 0.0;
    }

    @Override
    public SketchComponent<Number> copy() {
        SketchNumberComponent component = new SketchNumberComponent();
        component.value.setData(this.value.getData());
        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchNumberComponentPopup(this.value);
    }
}
