package com.terbah.sketch.natif.type;

import com.terbah.sketch.api.Copyable;

import java.util.ArrayList;

/**
 * @author Dorian TERBAH
 *
 * List of numbers.
 *
 * @version 1.0
 */
public class NumberList extends ArrayList<Number> implements Copyable<NumberList> {

    public NumberList() {
        super();
    }

    @Override
    public NumberList copy() {
        NumberList numberList = new NumberList();
        numberList.addAll(this);
        return numberList;
    }
}
