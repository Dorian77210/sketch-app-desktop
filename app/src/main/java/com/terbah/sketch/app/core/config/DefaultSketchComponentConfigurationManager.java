package com.terbah.sketch.app.core.config;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.app.core.config.validator.SketchComponentMissingRequiredAnnotation;
import com.terbah.sketch.app.core.config.validator.SketchComponentValidator;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DefaultSketchComponentConfigurationManager implements SketchComponentConfigurationManager {

    private final Map<Class<? extends SketchComponent<?>>, SketchComponentConfiguration> configurations;

    private Logger logger;

    /**
     * List of available validators
     */
    @Autowired
    private List<SketchComponentValidator> validators;

    public DefaultSketchComponentConfigurationManager() {
        this.configurations = new HashMap<>();
    }

    @PostConstruct
    public void setLogger() {
        this.logger = SketchLoggerManager.getLogger(this.getClass());
    }

    @Override
    public void registerComponent(Class<? extends SketchComponent<?>> componentClass) {
        try {
            this.validate(componentClass);
        } catch (SketchComponentMissingRequiredAnnotation exception) {
            this.logger.log(Level.SEVERE, String.format("Error during the register of the component %s", componentClass), exception);
            return;
        }

        this.logger.log(Level.FINE, "Register component {0} ", componentClass);

        ComponentConfiguration componentConfigurationAnnotation = componentClass.getAnnotation(ComponentConfiguration.class);
        String componentName = componentConfigurationAnnotation.name();
        String namespace = componentConfigurationAnnotation.namespace();
        List<SketchComponentEntry> entries = new ArrayList<>();
        SketchComponentConfiguration configuration = new SketchComponentConfiguration(componentName, namespace);

        try {
            configuration.setReturnType(componentClass.getMethod("execute").getReturnType());
        } catch (NoSuchMethodException e) {
            this.logger.log(Level.SEVERE, "Exception thrown", e);
        }

        Method[] methods = componentClass.getDeclaredMethods();

        for (Method method : methods) {
            if (this.isInjectableMethod(method)) {
                // register this method
                MethodInjectable annotation = method.getAnnotation(MethodInjectable.class);
                String paramName = annotation.value();
                int order = annotation.order();
                Class<?> paramType = method.getParameterTypes()[0];
                this.logger.log(Level.FINE, "Find the method to be an injectable method {0} ", method);
                entries.add(new SketchComponentEntry(paramName, method, paramType, order));
            }
        }

        // add the entries in the configuration, following the orders
        Collections.sort(entries);
        for (SketchComponentEntry entry : entries) {
            configuration.addParameter(entry.entryName(), entry.method(), entry.entryType());
        }

        this.configurations.put(componentClass, configuration);
    }

    @Override
    public SketchComponentConfiguration getConfigurationByComponentClass(Class<? extends SketchComponent> componentClass) {
        return this.configurations.get(componentClass);
    }

    @Override
    public Map<Class<? extends SketchComponent<?>>, SketchComponentConfiguration> getConfigurations() {
        return this.configurations;
    }

    private boolean isInjectableMethod(Method method) {
        return method.isAnnotationPresent(MethodInjectable.class) && method.getParameterCount() == 1;
    }

    /**
     * Check if a component has the waiting annotations to well define a component.
     *
     * @param componentClass The component's class
     * @throws SketchComponentMissingRequiredAnnotation if there miss a required annotation in the component's class.
     */
    private void validate(Class<? extends SketchComponent<?>> componentClass) throws SketchComponentMissingRequiredAnnotation {
        for (SketchComponentValidator validator : this.validators) {
            if (!validator.validate(componentClass)) {
                throw new SketchComponentMissingRequiredAnnotation("The component " + componentClass + " is not a valid component. It doesn't pass the validator " + validator.getClass());
            }
        }
    }
}
