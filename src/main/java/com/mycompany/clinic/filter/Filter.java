package com.mycompany.clinic.filter;

import com.vaadin.data.ValueProvider;

import java.util.function.BiFunction;

public class Filter<E, T> {
    private ValueProvider<E, T> valueProvider;
    private T filterValue;
    private BiFunction<T, T, Boolean> function;

    public Filter(ValueProvider<E, T> valueProvider, T filterValue, BiFunction<T, T, Boolean> function) {
        this.valueProvider = valueProvider;
        this.filterValue = filterValue;
        this.function = function;
    }

    public void setFilterValue(T filterValue) {
        this.filterValue = filterValue;
    }

    public boolean filter(E value) {
        //System.out.println("fv " + filterValue);
        if (filterValue == null) {
            return true;
        }
        //System.out.println("v " + valueProvider.apply(value));
        return function.apply(valueProvider.apply(value), filterValue);
    }
}

