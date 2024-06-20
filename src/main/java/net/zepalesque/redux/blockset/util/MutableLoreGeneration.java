package net.zepalesque.redux.blockset.util;

public interface MutableLoreGeneration<T extends MutableLoreGeneration<?>> extends ReduxGeneration {
    T withLore(String lore);
}
