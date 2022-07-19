package com.terbah.sketch.app.core.config;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DefaultSketchComponentConfigurationManager implements SketchComponentConfigurationManager {

    private final Map<Class<? extends SketchComponent<?>>, SketchComponentConfiguration> configurations;

    private Logger logger;

    public DefaultSketchComponentConfigurationManager() {
        this.configurations = new HashMap<>();
    }

    @Autowired
    public void setLogger() {
        this.logger = SketchLoggerManager.getLogger(this.getClass());
    }

    @Override
    public void registerComponent(Class<? extends SketchComponent<?>> componentClass) {
        this.logger.log(Level.FINE, "Register component " + componentClass);

        SketchComponentConfiguration configuration = new SketchComponentConfiguration();
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

                this.logger.log(Level.FINE, "Find the method to be an injectable method " + method);

                configuration.addParameter(paramName, method, paramType);
            }
        }

        this.configurations.put(componentClass, configuration);
    }

    @Override
    public Optional<SketchComponentConfiguration> getConfigurationByComponentClass(Class<? extends SketchComponent<?>> componentClass) {
        return Optional.ofNullable(this.configurations.get(componentClass));
    }

    private boolean isInjectableMethod(Method method) {
        return method.isAnnotationPresent(MethodInjectable.class) && method.getParameterCount() == 1;
    }
}
