package hw2;

import java.util.HashMap;
import java.util.Set;

public class Assignment extends HashMap<Word, String> {
    public boolean isConsistent() {
        Set<Word> wordsAssigned = this.keySet();

        for (Word word : wordsAssigned) {
            Constraints relevantConstraints = word.constraints;
            for (Constraint constraint : relevantConstraints) {
                if (!constraint.isSatisfied(this)) return false;
            }
        }

        return true;
    }

    public void addAssignment(Word word, String string) {
        put(word, string);
    }

    public void removeAssignment(Word word) {
        remove(word);
    }

    public boolean isComplete(Word[] wordList) {
        return wordList.length == this.size();
    }
}
