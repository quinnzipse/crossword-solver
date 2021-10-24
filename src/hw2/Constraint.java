package hw2;

import java.awt.geom.Point2D;

public class Constraint {
    private final Word word1, word2;
    private final int wordIndex1, wordIndex2;

    public Constraint(Word word1, Word word2, int wordIndex1, int wordIndex2) {
        this.word1 = word1;
        this.word2 = word2;
        this.wordIndex1 = wordIndex1;
        this.wordIndex2 = wordIndex2;
    }

    public Constraint(Word word1, Word word2, Point2D intersection) {
        this.word1 = word1;
        this.word2 = word2;
        this.wordIndex1 = word1.getIndexAt(intersection);
        this.wordIndex2 = word2.getIndexAt(intersection);
    }

    public boolean containsWord(Word word) {
        return word == word1 || word == word2;
    }

    public boolean constraintSatisfied() {
        String word1Value = word1.getValue(),
                word2Value = word2.getValue();

        return word1Value.charAt(wordIndex1) == word2Value.charAt(wordIndex2);
    }

    public void print() {
        System.out.printf("%s at index %d is constrained with %s at index %d\n", word1, wordIndex1, word2, wordIndex2);
    }
}
