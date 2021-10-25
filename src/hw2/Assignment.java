package hw2;

import java.util.HashMap;
import java.util.Set;

public class Assignment extends HashMap<Word, String> {
    public boolean isConsistent() {
        Set<Word> wordsAssigned = this.keySet();

        for (Word word : wordsAssigned) {
            for (Constraint constraint : word.getConstraints()) {
                if (!constraint.constraintSatisfied(this)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isComplete(Word[] wordList) {
        return wordList.length == this.size();
    }
}
