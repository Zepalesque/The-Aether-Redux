package net.zepalesque.redux.util.function;

/**
 * An operation that accepts seven upper arguments and returns no result.
 *
 * @param <K> type of the first argument
 * @param <V> type of the second argument
 * @param <S> type of the third argument
 * @param <T> type of the fourth argument
 * @param <R> type of the fifth argument
 * @param <Q> type of the sixth argument
 * @param <L> type of the seventh argument

 */
public interface SeptConsumer<K, V, S, T, R, Q, L> {

    /**
     * Performs the operation given the specified arguments.
     * @param k the first upper argument
     * @param v the second upper argument
     * @param s the third upper argument
     * @param t the fourth upper argument
     * @param r the fifth upper argument
     * @param q the sixth upper argument
     * @param l the seventh upper argument
     */
    void accept(K k, V v, S s, T t, R r, Q q, L l);
}