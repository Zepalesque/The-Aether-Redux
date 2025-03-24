package net.zepalesque.redux.util.function;


/**
 * <p>A variety of multi-argument operators, ranging from three to eight inputs.</p>
 * @see java.util.function.UnaryOperator UnaryOperator
 * @see java.util.function.BinaryOperator BinaryOperator
 */
@SuppressWarnings("unused")
public class Operators {

    /**
     * An operator with three inputs. Longer name would be {@code 'TrinaryOperator'.}
     */
    public interface O3<T> extends Functions.F3<T, T, T, T> {}

    /**
     * An operator with four inputs. Longer name would be {@code 'QuaternaryOperator'.}
     */
    public interface O4<T> extends Functions.F4<T, T, T, T, T> {}

    /**
     * An operator with five inputs. Longer name would be {@code 'QuinaryOperator'.}
     */
    public interface O5<T> extends Functions.F5<T, T, T, T, T, T> {}

    /**
     * An operator with six inputs. Longer name would be {@code 'HexaryOperator'.}
     */
    public interface O6<T> extends Functions.F6<T, T, T, T, T, T, T> {}

    /**
     * An operator with seven inputs. Longer name would be {@code 'SeptenaryOperator'.}
     */
    public interface O7<T> extends Functions.F7<T, T, T, T, T, T, T, T> {}

    /**
     * An operator with eight inputs. Longer name would be {@code 'OctalOperator'.}
     */
    public interface O8<T> extends Functions.F8<T, T, T, T, T, T, T, T, T> {}
}
