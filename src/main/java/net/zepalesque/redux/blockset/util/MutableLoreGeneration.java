package net.zepalesque.redux.blockset.util;

import net.zepalesque.redux.blockset.flower.type.BaseFlowerSet;

public interface MutableLoreGeneration<T extends MutableLoreGeneration<T>> extends ReduxGeneration {
    T withLore(String lore);
}
