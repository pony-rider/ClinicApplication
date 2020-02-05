package com.mycompany.clinic.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterChain<T> {
    private List<Filter<T,?>> filters;

    public FilterChain(List<Filter<T,?>> filters) {
        this.filters = filters;
    }

    public FilterChain(Filter<T,?> filter) {
        filters = new ArrayList<>();
        addFilter(filter);
    }

    public FilterChain<T> addFilter(Filter<T,?> filter) {
        filters.add(filter);
        return this;
    }

    public FilterChain<T> removeFilter(Filter<T,?> filter) {
        filters.remove(filter);
        return this;
    }

    public boolean filter(T value) {
        for (Filter<T,?> filter : filters) {
            if (!filter.filter(value)) {
                return false;
            }
        }
        return true;
    }
}
