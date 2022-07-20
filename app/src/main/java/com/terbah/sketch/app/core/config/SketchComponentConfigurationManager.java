package com.terbah.sketch.app.core.config;

import com.terbah.sketch.api.SketchComponent;

import java.util.List;
import java.util.Map;

/**
 * @author Dorian TERBAH
 * <p>
 * Interface that defines the methods for a component configuration manager.
 * @since 1.0
 */
public interface SketchComponentConfigurationManager {

    /**
     * Register a component to create an associated configuration.
     *
     * @param componentClass The class of the component.
     */
    void registerComponent(Class<? extends SketchComponent<?>> componentClass);

    /**
     * @param componentClass The class for the component to get the associated configuration.
     * @return The configuration associated of the component.
     */
    SketchComponentConfiguration getConfigurationByComponentClass(Class<? extends SketchComponent> componentClass);

    /**
     * @return The list of all the available components.
     */
    Map<Class<? extends SketchComponent<?>>, SketchComponentConfiguration> getComponentsClass();
}
