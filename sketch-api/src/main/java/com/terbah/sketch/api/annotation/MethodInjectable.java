package com.terbah.sketch.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dorian TERBAH
 * <p>
 * This annotation is used to specify a method that can be used
 * by the sketch app to inject execution data. When you use this annotation,
 * you must use an id to identify the data.
 * @since 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodInjectable {
    String value();
}
