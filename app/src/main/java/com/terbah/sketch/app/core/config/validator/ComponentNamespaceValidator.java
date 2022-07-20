package com.terbah.sketch.app.core.config.validator;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentNamespace;
import org.springframework.stereotype.Service;

/**
 * @author Dorian TERBAH
 *
 * This class will check if a component has the annotation ComponentNamespace present.
 *
 * @version 1.0
 * @see ComponentNamespace
 */

@Service
public class ComponentNamespaceValidator implements SketchComponentValidator {

    @Override
    public boolean validate(Class<? extends SketchComponent<?>> componentType) {
        return componentType.isAnnotationPresent(ComponentNamespace.class);
    }
}
