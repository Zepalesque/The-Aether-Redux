package net.zepalesque.redux.block.state.enums;

import net.minecraft.util.StringRepresentable;

import java.util.function.BinaryOperator;

public enum LogicatorMode implements StringRepresentable {
    
    AND("and", Boolean::logicalAnd), // a AND b = c
    //  a | b | c
    // ---|---|---
    //  1 | 1 | 1
    //  1 | 0 | 0
    //  0 | 1 | 0
    //  0 | 0 | 0
    OR("or", Boolean::logicalOr), // a OR b = c
    //  a | b | c
    // ---|---|---
    //  1 | 1 | 1
    //  1 | 0 | 1
    //  0 | 1 | 1
    //  0 | 0 | 0
    XNOR("xnor", (b1, b2) -> b1 == b2), // a XNOR b = c
    //  a | b | c
    // ---|---|---
    //  1 | 1 | 1
    //  1 | 0 | 0
    //  0 | 1 | 0
    //  0 | 0 | 1
    XOR("xor", Boolean::logicalXor); // a XOR b = c
    //  a | b | c
    // ---|---|---
    //  1 | 1 | 0
    //  1 | 0 | 1
    //  0 | 1 | 1
    //  0 | 0 | 0

    final String name;
    final BinaryOperator<Boolean> operator;
    LogicatorMode(String name, BinaryOperator<Boolean> operator) {
        this.name = name;
        this.operator = operator;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public boolean operate(boolean b1, boolean b2) {
        return this.operator.apply(b1, b2);
    }

    public LogicatorMode flipOperationType() {
        return switch (this) {
            case OR -> AND;
            case AND -> OR;
            case XOR -> XNOR;
            case XNOR -> XOR;
        };
    }
    public LogicatorMode flipExclusivity() {
        return switch (this) {
            case OR -> XOR;
            case AND -> XNOR;
            case XOR -> OR;
            case XNOR -> AND;
        };
    }

    public LogicatorMode withExclusivity(boolean exclusivity) {
        return this.isExclusive() == exclusivity ? this : this.flipExclusivity();
    }

    public boolean isExclusive() {
        return this == XOR || this == XNOR;
    }

    public boolean isOr() {
        return this == OR || this == XOR;
    }

    public static LogicatorMode getMode(boolean isExclusive, boolean isOr) {
        return !isExclusive ? !isOr ? AND : OR : !isOr ? XNOR : XOR;
    }
}
