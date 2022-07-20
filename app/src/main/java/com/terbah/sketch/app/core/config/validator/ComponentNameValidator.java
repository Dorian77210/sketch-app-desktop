package com.terbah.sketch.app.core.config.validator;

import com.terbah.sketch.api.annotation.ComponentName;
import com.terbah.sketch.api.SketchComponent;
import org.springframework.stereotype.Service;

/**
 * @author Dorian TERBAH
 *
 * This class will check if a component class has the annotation ComponentName
 *
 * @since 1.0
 * @see ComponentName
 */

@Service
public class ComponentNameValidator implements SketchComponentValidator {

    @Override
    public boolean validate(Class<? extends SketchComponent<?>> componentType) {
        return componentType.isAnnotationPresent(ComponentName.class);
    }
}
