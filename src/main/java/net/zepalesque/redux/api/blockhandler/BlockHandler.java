package net.zepalesque.redux.api.blockhandler;

public interface BlockHandler {

    static boolean isVowel(char c)
    {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    void generateBlockstateData(ReduxBlockstateData data);
    void generateItemModels(ReduxItemModelData data);
    void generateLanguageData(ReduxLanguageData data);
}
