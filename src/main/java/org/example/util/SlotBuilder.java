package org.example.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SlotBuilder<T> {
    private List<T> list;
    private final int size;
    private final Class<T> type;

    public SlotBuilder(int size, Class<T> type) {
        this.size = size;
        this.type = type;
        this.list = new ArrayList<>(size);
    }

    public static <T> SlotBuilder<T> of(int size, Class<T> type) {
        return new SlotBuilder<T>(size, type);
    }

    public SlotBuilder<T> skip() {
        checkBounds(1);
        list.add(null);
        return this;
    }

    public SlotBuilder<T> skip(int n) {
        checkBounds(n);
        for (int i = 0; i < n; i++) {
            list.add(null);
        }
        return this;
    }

    public SlotBuilder<T> item(T... items) {
        checkBounds(items.length);
        list.addAll(Arrays.asList(items));
        return this;
    }

    public SlotBuilder<T> items(List<T> items) {
        checkBounds(items.size());
        list.addAll(items);
        return this;
    }

    public T[] make() {
        while (list.size() < size) {
            list.add(null);
        }
        return list.toArray((T[]) java.lang.reflect.Array.newInstance(type, size));
    }

    private void checkBounds(int n) {
        if (list.size() + n > size) {
            throw new IndexOutOfBoundsException("Exceeded the predefined size.");
        }
    }
}
