package com.terbah.sketch.app.core.config;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentName;
import com.terbah.sketch.api.annotation.ComponentNamespace;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.app.core.config.validator.SketchComponentMissingRequiredAnnotation;
import com.terbah.sketch.app.core.config.validator.SketchComponentValidator;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
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

        this.logger.log(Level.FINE, "Register component {} ", componentClass);

        String componentName = componentClass.getAnnotation(ComponentName.class).value();
        String namespace = componentClass.getAnnotation(ComponentNamespace.class).value();

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
                String paramName = method.getAnnotation(MethodInjectable.class).value();
                Class<?> paramType = method.getParameterTypes()[0];

                this.logger.log(Level.FINE, "Find the method to be an injectable method {} ", method);

                configuration.addParameter(paramName, method, paramType);
            }
        }

        this.configurations.put(componentClass, configuration);
    }

    @Override
    public SketchComponentConfiguration getConfigurationByComponentClass(Class<? extends SketchComponent> componentClass) {
        return this.configurations.get(componentClass);
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
