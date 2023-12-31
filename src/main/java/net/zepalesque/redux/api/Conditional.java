package net.zepalesque.redux.api;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A class encapsulating a value that only returns it under if a certain condition is met.
 * The condition can also be overriden, so that the true value can be obtained regardless of whether or not the condition is met.
 * @param <T>
 */
public class Conditional<T> {

    private final T value;
    private final Supplier<Boolean> condition;

    private Conditional(T value, Supplier<Boolean> condition)
    {
        this.value = value;
        this.condition = condition;
    }
    public static<T> Conditional<T> never(T val) {
        return new Conditional<>(val, () -> false);
    }
    public static<T> Conditional<T> always(T val) {
        return new Conditional<>(val, () -> true);
    }
    public static <T> Conditional<T> of(T value, Supplier<Boolean> condition) {
        return new Conditional<>(value, condition);
    }

    public T get() {
        return this.condition.get() ? value : null;
    }

    public boolean isUnavailable() {
        return !this.isAvailable();
    }

    public boolean isAvailable() {
        return condition.get() && value != null;
    }

    public boolean isEmpty() {
        return value == null;
    }
    public boolean isPopulated() {
        return value != null;
    }


    public void ifAvailable(Consumer<? super T> action) {
        if (this.isAvailable()) {
            action.accept(value);
        }
    }

    public T getAnyway() {
        return this.value;
    }

    public void ifAvailableOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (this.isAvailable()) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    public T orElse(T other) {
        return this.isAvailable() ? value : other;
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        return this.isAvailable() ? value : supplier.get();
    }
    public T orElseThrow() {
        if (this.isUnavailable()) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.isAvailable()) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }




}
