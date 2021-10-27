package hw2;

import java.util.*;

public class Assignment extends HashMap<Word, String> {
    public Assignment() {
        super();
    }

    public Assignment(Assignment a) {
        super(a);
    }

    public boolean isConsistent() {
        Set<Word> wordsAssigned = this.keySet();

        for (Word word : wordsAssigned) {
            Constraints relevantConstraints = word.constraints;
            for (Constraint constraint : relevantConstraints) {
                if (!constraint.isSatisfied(this)) return false;
                Word other = constraint.getOtherWord(word);

                if (isAssigned(other)) continue;

                int possibilities = 0;
                Assignment inner = new Assignment(this);
                for (String d : other.getDomain()) {
                    inner.addAssignment(other, d);
                    if (other.isConsistent(inner)) possibilities++;
                    inner.removeAssignment(other);
                }
                if (possibilities == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isAssigned(Word word) {
        return containsKey(word);
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
