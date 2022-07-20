package com.terbah.sketch.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dorian TERBAH
 *
 * This annotation permits to give a namespace to a component.
 * The value must respect the following format : lib/a/b
 *
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentNamespace {
    String value();
}
