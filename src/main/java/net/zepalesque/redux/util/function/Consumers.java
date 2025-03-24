package net.zepalesque.redux.util.function;

import org.jetbrains.annotations.NotNull;

/**
 * <p>A variety of multi-argument consumers, ranging from three to eight inputs.</p>
 * @see java.util.function.Consumer Consumer
 * @see java.util.function.BiConsumer BiConsumer
 */
@SuppressWarnings("unused")
public class Consumers {

    /**
     * A consumer with three inputs. Longer name would be {@code 'TriConsumer'.}
     */
    public interface C3<T1, T2, T3> {

        /**
         * Performs this operation on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         */
        void accept(T1 t1, T2 t2, T3 t3);

        /**
         * Returns a composed consumer that performs, in sequence, this
         * operation followed by the {@code after} operation. If performing either
         * operation throws an exception, it is relayed to the caller of the
         * composed operation.  If performing this operation throws an exception,
         * the {@code after} operation will not be performed.
         *
         * @param after the operation to perform after this operation
         * @return a composed consumer that performs in sequence this
         * operation followed by the {@code after} operation
         */
        default C3<T1, T2, T3> andThen(@NotNull C3<? super T1, ? super T2, ? super T3> after) {
            return (t1, t2, t3) -> { accept(t1, t2, t3); after.accept(t1, t2, t3); };
        }
    }

    /**
     * A consumer with four inputs. Longer name would be {@code 'QuatConsumer'.}
     */
    public interface C4<T1, T2, T3, T4> {

        /**
         * Performs this operation on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         */
        void accept(T1 t1, T2 t2, T3 t3, T4 t4);

        /**
         * Returns a composed consumer that performs, in sequence, this
         * operation followed by the {@code after} operation. If performing either
         * operation throws an exception, it is relayed to the caller of the
         * composed operation.  If performing this operation throws an exception,
         * the {@code after} operation will not be performed.
         *
         * @param after the operation to perform after this operation
         * @return a composed consumer that performs in sequence this
         * operation followed by the {@code after} operation
         */
        default C4<T1, T2, T3, T4> andThen(@NotNull C4<? super T1, ? super T2, ? super T3, ? super T4> after) {
            return (t1, t2, t3, t4) -> { accept(t1, t2, t3, t4); after.accept(t1, t2, t3, t4); };
        }
    }

    /**
     * A consumer with five inputs. Longer name would be {@code 'QuinConsumer'.}
     */
    public interface C5<T1, T2, T3, T4, T5> {

        /**
         * Performs this operation on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         */
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);

        /**
         * Returns a composed consumer that performs, in sequence, this
         * operation followed by the {@code after} operation. If performing either
         * operation throws an exception, it is relayed to the caller of the
         * composed operation.  If performing this operation throws an exception,
         * the {@code after} operation will not be performed.
         *
         * @param after the operation to perform after this operation
         * @return a composed consumer that performs in sequence this
         * operation followed by the {@code after} operation
         */
        default C5<T1, T2, T3, T4, T5> andThen(@NotNull C5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5> after) {
            return (t1, t2, t3, t4, t5) -> { accept(t1, t2, t3, t4, t5); after.accept(t1, t2, t3, t4, t5); };
        }
    }

    /**
     * A consumer with six inputs. Longer name would be {@code 'HexConsumer'.}
     */
    public interface C6<T1, T2, T3, T4, T5, T6> {

        /**
         * Performs this operation on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         * @param t6 the sixth input argument
         */
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);

        /**
         * Returns a composed consumer that performs, in sequence, this
         * operation followed by the {@code after} operation. If performing either
         * operation throws an exception, it is relayed to the caller of the
         * composed operation.  If performing this operation throws an exception,
         * the {@code after} operation will not be performed.
         *
         * @param after the operation to perform after this operation
         * @return a composed consumer that performs in sequence this
         * operation followed by the {@code after} operation
         */
        default C6<T1, T2, T3, T4, T5, T6> andThen(@NotNull C6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6> after) {
            return (t1, t2, t3, t4, t5, t6) -> { accept(t1, t2, t3, t4, t5, t6); after.accept(t1, t2, t3, t4, t5, t6); };
        }
    }

    /**
     * A consumer with seven inputs. Longer name would be {@code 'SeptConsumer'.}
     */
    public interface C7<T1, T2, T3, T4, T5, T6, T7> {

        /**
         * Performs this operation on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         * @param t6 the sixth input argument
         * @param t7 the seventh input argument
         */
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);

        /**
         * Returns a composed consumer that performs, in sequence, this
         * operation followed by the {@code after} operation. If performing either
         * operation throws an exception, it is relayed to the caller of the
         * composed operation.  If performing this operation throws an exception,
         * the {@code after} operation will not be performed.
         *
         * @param after the operation to perform after this operation
         * @return a composed consumer that performs in sequence this
         * operation followed by the {@code after} operation
         */
        default C7<T1, T2, T3, T4, T5, T6, T7> andThen(@NotNull C7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7> after) {
            return (t1, t2, t3, t4, t5, t6, t7) -> { accept(t1, t2, t3, t4, t5, t6, t7); after.accept(t1, t2, t3, t4, t5, t6, t7); };
        }
    }

    /**
     * A consumer with eight inputs. Longer name would be {@code 'OctConsumer'.}
     */
    public interface C8<T1, T2, T3, T4, T5, T6, T7, T8> {

        /**
         * Performs this operation on the given arguments.
         *
         * @param t1 the first input argument
         * @param t2 the second input argument
         * @param t3 the third input argument
         * @param t4 the fourth input argument
         * @param t5 the fifth input argument
         * @param t6 the sixth input argument
         * @param t7 the seventh input argument
         * @param t8 the eighth input argument
         */
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);

        /**
         * Returns a composed consumer that performs, in sequence, this
         * operation followed by the {@code after} operation. If performing either
         * operation throws an exception, it is relayed to the caller of the
         * composed operation.  If performing this operation throws an exception,
         * the {@code after} operation will not be performed.
         *
         * @param after the operation to perform after this operation
         * @return a composed consumer that performs in sequence this
         * operation followed by the {@code after} operation
         */
        default C8<T1, T2, T3, T4, T5, T6, T7, T8> andThen(@NotNull C8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8> after) {
            return (t1, t2, t3, t4, t5, t6, t7, t8) -> { accept(t1, t2, t3, t4, t5, t6, t7, t8); after.accept(t1, t2, t3, t4, t5, t6, t7, t8); };
        }
    }
}
