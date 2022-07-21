package com.terbah.sketch.app.core.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Dorian TERBAH
 * <p>
 * This class represents a configuration of a component.
 * It will be composed of the return type of the component's
 * execute method, the data injectable (id and type)
 * @since 1.0
 */
public class SketchComponentConfiguration {

    /**
     * Map an id with the method/parameter type of the corresponding
     * annotation in the components.
     */
    private final Map<String, SketchInjectableMethod> methodsToInject;

    /**
     * Store the return type of component.
     */
    private Class<?> returnType;

    /**
     * Name of the component.
     */
    private final String componentName;

    /**
     * Namespace of the component.
     */
    private final String namespace;

    /**
     * Constructor of the class <code>SketchComponentConfiguration</code>
     */
    public SketchComponentConfiguration(String componentName, String namespace) {
        this.methodsToInject = new HashMap<>();
        this.returnType = null;

        this.componentName = componentName;
        this.namespace = namespace;
    }

    /**
     * @return The name of the component.
     */
    public String getComponentName() {
        return this.componentName;
    }

    /**
     * @return The namespace.
     */
    public String getNamespace() {
        return this.namespace;
    }

    /**
     * @return the return type of the corresponding component class.
     */
    public Class<?> getReturnType() {
        return this.returnType;
    }

    /**
     * Update the return type of the corresponding component class.
     *
     * @param returnType The new value
     */
    void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    void addParameter(String paramName, Method method, Class<?> paramType) {
        this.methodsToInject.put(paramName, new SketchInjectableMethod(method, paramType));
    }

    public boolean acceptParameter(String param, Class<?> type) {
        SketchInjectableMethod method = this.methodsToInject.get(param);
        return method.paramType.isAssignableFrom(type);
    }

    public boolean hasMethodInjectableFor(String entryName) {
        return this.methodsToInject.containsKey(entryName);
    }

    public Optional<SketchInjectableMethod> getMethodInjectable(String entryName) {
        return Optional.ofNullable(this.methodsToInject.get(entryName));
    }

    /**
     * @author Dorian TERBAH
     * <p>
     * Class that stored an association between a method, and it's first parameter type.
     * @version 1.0
     */
    public static class SketchInjectableMethod {

        /**
         * The associated method.
         */
        private final Method method;

        /**
         * The type of the parameter.
         */
        private final Class<?> paramType;

        public SketchInjectableMethod(final Method method, Class<?> paramType) {
            this.method = method;
            this.paramType = paramType;
        }

        public Class<?> getParamType() {
            return paramType;
        }

        public Method getMethod() {
            return method;
        }
    }
}
