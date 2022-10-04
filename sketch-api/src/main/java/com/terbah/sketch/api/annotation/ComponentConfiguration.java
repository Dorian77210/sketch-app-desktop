package com.terbah.sketch.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Dorian TERBAH
 *
 * This annotation permits to user to specify
 * all the required information of a component
 * (namespace, name, ...)
 *
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentConfiguration {
    String namespace();
    String name();
}
