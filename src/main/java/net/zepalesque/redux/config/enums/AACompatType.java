package net.zepalesque.redux.config.enums;

public enum AACompatType implements NamedConfig {
    TRUE, FALSE, WITHOUT_AA;

    @Override
    public String serialize() {
        return this.name().toLowerCase();
    }
}
