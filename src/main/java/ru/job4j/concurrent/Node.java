package ru.job4j.concurrent;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        throw new IllegalStateException(
                String.format("Could not set Node.next to %s. Class is immutable.", next)
        );
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        throw new IllegalStateException(
                String.format("Could not set value to %s. Class is immutable.", value)
        );
    }
}
