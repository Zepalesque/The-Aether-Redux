package net.zepalesque.redux.util.function;

import org.jetbrains.annotations.NotNull;

/**
 * <p>A variety of multi-argument predicates, ranging from three to eight inputs.</p>
 * @see java.util.function.Predicate Predicate
 * @see java.util.function.BiPredicate BiPredicate
 */
@SuppressWarnings("unused")
public class Predicates {

    /**
     * A predicate with three inputs. Longer name would be {@code 'TriPredicate'.}
     */
    public interface P3<T1, T2, T3> {

        /**
         * Evaluates this predicate on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @return {@code true} if the input arguments match the predicate,
         * otherwise {@code false}
         */
        boolean test(T1 t1, T2 t2, T3 t3);

        /**
         * Returns a predicate that represents the logical negation of this
         * predicate.
         *
         * @return a predicate that represents the logical negation of this
         * predicate
         */
        default P3<T1, T2, T3> negate() {
            return (t1, t2, t3) -> !test(t1, t2, t3);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * AND of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code false}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ANDed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * AND of this predicate and the {@code other} predicate
         */
        default P3<T1, T2, T3> and(@NotNull P3<? super T1, ? super T2, ? super T3> other) {
            return (t1, t2, t3) -> test(t1, t2, t3) && other.test(t1, t2, t3);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * OR of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code true}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ORed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * OR of this predicate and the {@code other} predicate
         */
        default P3<T1, T2, T3> or(@NotNull P3<? super T1, ? super T2, ? super T3> other) {
            return (t1, t2, t3) -> test(t1, t2, t3) || other.test(t1, t2, t3);
        }
    }

    /**
     * A predicate with four inputs. Longer name would be {@code 'QuatPredicate'.}
     */
    public interface P4<T1, T2, T3, T4> {

        /**
         * Evaluates this predicate on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @return {@code true} if the input arguments match the predicate,
         * otherwise {@code false}
         */
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4);

        /**
         * Returns a predicate that represents the logical negation of this
         * predicate.
         *
         * @return a predicate that represents the logical negation of this
         * predicate
         */
        default P4<T1, T2, T3, T4> negate() {
            return (t1, t2, t3, t4) -> !test(t1, t2, t3, t4);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * AND of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code false}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ANDed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * AND of this predicate and the {@code other} predicate
         */
        default P4<T1, T2, T3, T4> and(@NotNull P4<? super T1, ? super T2, ? super T3, ? super T4> other) {
            return (t1, t2, t3, t4) -> test(t1, t2, t3, t4) && other.test(t1, t2, t3, t4);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * OR of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code true}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ORed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * OR of this predicate and the {@code other} predicate
         */
        default P4<T1, T2, T3, T4> or(@NotNull P4<? super T1, ? super T2, ? super T3, ? super T4> other) {
            return (t1, t2, t3, t4) -> test(t1, t2, t3, t4) || other.test(t1, t2, t3, t4);
        }
    }

    /**
     * A predicate with five inputs. Longer name would be {@code 'QuinPredicate'.}
     */
    public interface P5<T1, T2, T3, T4, T5> {

        /**
         * Evaluates this predicate on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         * @return {@code true} if the input arguments match the predicate,
         * otherwise {@code false}
         */
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);

        /**
         * Returns a predicate that represents the logical negation of this
         * predicate.
         *
         * @return a predicate that represents the logical negation of this
         * predicate
         */
        default P5<T1, T2, T3, T4, T5> negate() {
            return (t1, t2, t3, t4, t5) -> !test(t1, t2, t3, t4, t5);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * AND of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code false}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ANDed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * AND of this predicate and the {@code other} predicate
         */
        default P5<T1, T2, T3, T4, T5> and(@NotNull P5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5> other) {
            return (t1, t2, t3, t4, t5) -> test(t1, t2, t3, t4, t5) && other.test(t1, t2, t3, t4, t5);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * OR of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code true}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ORed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * OR of this predicate and the {@code other} predicate
         */
        default P5<T1, T2, T3, T4, T5> or(@NotNull P5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5> other) {
            return (t1, t2, t3, t4, t5) -> test(t1, t2, t3, t4, t5) || other.test(t1, t2, t3, t4, t5);
        }
    }

    /**
     * A predicate with six inputs. Longer name would be {@code 'HexPredicate'.}
     */
    public interface P6<T1, T2, T3, T4, T5, T6> {

        /**
         * Evaluates this predicate on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         * @param t6 the sixth input argument
         * @return {@code true} if the input arguments match the predicate,
         * otherwise {@code false}
         */
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);

        /**
         * Returns a predicate that represents the logical negation of this
         * predicate.
         *
         * @return a predicate that represents the logical negation of this
         * predicate
         */
        default P6<T1, T2, T3, T4, T5, T6> negate() {
            return (t1, t2, t3, t4, t5, t6) -> !test(t1, t2, t3, t4, t5, t6);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * AND of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code false}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ANDed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * AND of this predicate and the {@code other} predicate
         */
        default P6<T1, T2, T3, T4, T5, T6> and(@NotNull P6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6> other) {
            return (t1, t2, t3, t4, t5, t6) -> test(t1, t2, t3, t4, t5, t6) && other.test(t1, t2, t3, t4, t5, t6);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * OR of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code true}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ORed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * OR of this predicate and the {@code other} predicate
         */
        default P6<T1, T2, T3, T4, T5, T6> or(@NotNull P6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6> other) {
            return (t1, t2, t3, t4, t5, t6) -> test(t1, t2, t3, t4, t5, t6) || other.test(t1, t2, t3, t4, t5, t6);
        }
    }

    /**
     * A predicate with seven inputs. Longer name would be {@code 'SeptPredicate'.}
     */
    public interface P7<T1, T2, T3, T4, T5, T6, T7> {

        /**
         * Evaluates this predicate on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         * @param t6 the sixth input argument
         * @param t7 the seventh input argument
         * @return {@code true} if the input arguments match the predicate,
         * otherwise {@code false}
         */
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);

        /**
         * Returns a predicate that represents the logical negation of this
         * predicate.
         *
         * @return a predicate that represents the logical negation of this
         * predicate
         */
        default P7<T1, T2, T3, T4, T5, T6, T7> negate() {
            return (t1, t2, t3, t4, t5, t6, t7) -> !test(t1, t2, t3, t4, t5, t6, t7);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * AND of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code false}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ANDed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * AND of this predicate and the {@code other} predicate
         */
        default P7<T1, T2, T3, T4, T5, T6, T7> and(@NotNull P7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7> other) {
            return (t1, t2, t3, t4, t5, t6, t7) -> test(t1, t2, t3, t4, t5, t6, t7) && other.test(t1, t2, t3, t4, t5, t6, t7);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * OR of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code true}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ORed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * OR of this predicate and the {@code other} predicate
         */
        default P7<T1, T2, T3, T4, T5, T6, T7> or(@NotNull P7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7> other) {
            return (t1, t2, t3, t4, t5, t6, t7) -> test(t1, t2, t3, t4, t5, t6, t7) || other.test(t1, t2, t3, t4, t5, t6, t7);
        }
    }

    /**
     * A predicate with eight inputs. Longer name would be {@code 'OctPredicate'.}
     */
    public interface P8<T1, T2, T3, T4, T5, T6, T7, T8> {

        /**
         * Evaluates this predicate on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         * @param t6 the sixth input argument
         * @param t7 the seventh input argument
         * @param t8 the eighth input argument
         * @return {@code true} if the input arguments match the predicate,
         * otherwise {@code false}
         */
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);

        /**
         * Returns a predicate that represents the logical negation of this
         * predicate.
         *
         * @return a predicate that represents the logical negation of this
         * predicate
         */
        default P8<T1, T2, T3, T4, T5, T6, T7, T8> negate() {
            return (t1, t2, t3, t4, t5, t6, t7, t8) -> !test(t1, t2, t3, t4, t5, t6, t7, t8);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * AND of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code false}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ANDed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * AND of this predicate and the {@code other} predicate
         */
        default P8<T1, T2, T3, T4, T5, T6, T7, T8> and(@NotNull P8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8> other) {
            return (t1, t2, t3, t4, t5, t6, t7, t8) -> test(t1, t2, t3, t4, t5, t6, t7, t8) && other.test(t1, t2, t3, t4, t5, t6, t7, t8);
        }

        /**
         * Returns a composed predicate that represents a short-circuiting logical
         * OR of this predicate and another.  When evaluating the composed
         * predicate, if this predicate is {@code true}, then the {@code other}
         * predicate is not evaluated.
         *
         * <p>Any exceptions thrown during evaluation of either predicate are relayed
         * to the caller; if evaluation of this predicate throws an exception, the
         * {@code other} predicate will not be evaluated.
         *
         * @param other a predicate that will be logically-ORed with this
         *              predicate
         * @return a composed predicate that represents the short-circuiting logical
         * OR of this predicate and the {@code other} predicate
         */
        default P8<T1, T2, T3, T4, T5, T6, T7, T8> or(@NotNull P8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8> other) {
            return (t1, t2, t3, t4, t5, t6, t7, t8) -> test(t1, t2, t3, t4, t5, t6, t7, t8) || other.test(t1, t2, t3, t4, t5, t6, t7, t8);
        }
    }
}
