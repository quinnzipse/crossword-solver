package hw2;

import java.awt.geom.Point2D;

public class Constraint {
    public final Word word1, word2;
    private final int wordIndex1, wordIndex2;

    public Constraint(Word word1, Word word2, Point2D intersection) {
        this.word1 = word1;
        this.word2 = word2;
        this.wordIndex1 = word1.getIndexAt(intersection);
        this.wordIndex2 = word2.getIndexAt(intersection);

        word1.addConstraint(this);
        word2.addConstraint(this);
    }

    public boolean isSatisfied(Assignment assignment) {
        String word1Value = assignment.get(word1),
                word2Value = assignment.get(word2);

        if (word1Value == null || word2Value == null) return true;
        return word1Value.charAt(wordIndex1) == word2Value.charAt(wordIndex2);
    }

    public Word getOtherWord(Word word) {
        if (word == word1) return word2;
        return word1;
    }
}
