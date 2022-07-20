package com.terbah.sketch.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dorian TERBAH
 *
 * This annotation permits to give a name to a component.
 * This name will be visible on the corresponding UI
 * of a component.
 *
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentName {
    String value();
}
