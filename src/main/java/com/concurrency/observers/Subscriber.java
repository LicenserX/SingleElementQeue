package com.concurrency.observers;

@FunctionalInterface
public interface Subscriber {

    void update(Object o);
}
