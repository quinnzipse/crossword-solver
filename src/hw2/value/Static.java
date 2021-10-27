package hw2.value;

import hw2.Word;

public class Static extends ValueOrderer {
    @Override
    public String[] order(Word word) {
        return word.getDomain();
    }
}
