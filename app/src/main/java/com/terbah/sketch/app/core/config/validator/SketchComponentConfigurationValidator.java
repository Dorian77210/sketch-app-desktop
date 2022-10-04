package com.terbah.sketch.app.core.config.validator;

import com.terbah.sketch.api.SketchComponent;

import com.terbah.sketch.api.annotation.ComponentConfiguration;
import org.springframework.stereotype.Service;

/**
 * @author Dorian TERBAH
 *
 * Validator that will verify that the annotation ComponentConfiguration
 * is present for a component.
 *
 * @see ComponentConfiguration
 * @since 1.0
 */

@Service
public class SketchComponentConfigurationValidator implements SketchComponentValidator{
    @Override
    public boolean validate(Class<? extends SketchComponent<?>> componentType) {
        return componentType.isAnnotationPresent(ComponentConfiguration.class);
    }
}
