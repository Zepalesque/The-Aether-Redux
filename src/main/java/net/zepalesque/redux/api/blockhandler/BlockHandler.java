package net.zepalesque.redux.api.blockhandler;

import net.zepalesque.redux.data.ReduxBlockstateData;
import net.zepalesque.redux.data.ReduxItemModelData;
import net.zepalesque.redux.data.ReduxLanguageData;

public interface BlockHandler {

    static boolean isVowel(char c)
    {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    void generateBlockstateData(ReduxBlockstateData data);
    void generateItemModels(ReduxItemModelData data);
    void generateLanguageData(ReduxLanguageData data);
}
