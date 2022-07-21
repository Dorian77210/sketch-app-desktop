package com.terbah.sketch.app.core.config;

import java.lang.reflect.Method;

/**
 * @author Dorian TERBAH
 *
 * This record stores usefull information about an entry
 *
 * @version 1.0
 */
record SketchComponentEntry(String entryName, Method method, Class<?> entryType, int order) implements Comparable<SketchComponentEntry> {

    @Override
    public int compareTo(SketchComponentEntry entry) {
        return Integer.compare(this.order, entry.order);
    }
}
