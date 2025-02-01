package net.zepalesque.redux.config.enums;

public interface SerializedConfig extends CharSequence {

    String serialized();

    @Override
    default int length() {
        return this.serialized().length();
    }

    @Override
    default char charAt(int index) {
        return this.serialized().charAt(index);
    }

    @Override
    default CharSequence subSequence(int start, int end) {
        return this.serialized().subSequence(start, end);
    }
}
