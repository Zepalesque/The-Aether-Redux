package net.zepalesque.redux.util.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class StateUtil {

    public static <P extends Comparable<P>> BlockState mapValue(BlockState state, Property<P> property, UnaryOperator<P> operation) {
        P original = state.getValue(property);
        P mapped = operation.apply(original);
        return state.setValue(property, mapped);
    }

    public static <P extends Comparable<P>> BlockState setIfDifferent(BlockState state, Property<P> property, UnaryOperator<P> operation) {
        P original = state.getValue(property);
        P mapped = operation.apply(original);
        return state.setValue(property, mapped);
    }
}
