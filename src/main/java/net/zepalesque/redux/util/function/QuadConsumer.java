package net.zepalesque.redux.util.function;

/**
 * An operation that accepts four upper arguments and returns no result.
 *
 * @param <K> type of the first argument
 * @param <V> type of the second argument
 * @param <S> type of the third argument
 * @param <T> type of the fourth argument

 */
public interface QuadConsumer<K, V, S, T> {

    /**
     * Performs the operation given the specified arguments.
     * @param k the first upper argument
     * @param v the second upper argument
     * @param s the third upper argument
     * @param t the fourth upper argument
     */
    void accept(K k, V v, S s, T t);
}