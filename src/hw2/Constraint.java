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

    public boolean constraintSatisfied(Assignment assignment) {
        String word1Value = assignment.get(word1),
                word2Value = assignment.get(word2);

        if (word1Value == null || word2Value == null) return true;
        return word1Value.charAt(wordIndex1) == word2Value.charAt(wordIndex2);
    }

    public Word getWord1() {
        return word1;
    }

    public Word getWord2() {
        return word2;
    }

    public void print() {
        Point2D word1Location = word1.getLocation(), word2Location = word2.getLocation();

        System.out.printf("(%.0f, %.0f) at index %d is constrained with (%.0f, %.0f) at index %d\n",
                word1Location.getX(), word1Location.getY(), wordIndex1,
                word2Location.getX(), word2Location.getY(), wordIndex2);
    }
}
