package hw2;

import java.util.Arrays;

public class CSP {
    private final Word[] words;
    private final Dictionaries domains;
    private final Constraints constraints;
    private final PuzzleKey puzzleKey;

    public CSP(PuzzleKey puzzleKey, Dictionaries domains) {
        this.puzzleKey = puzzleKey;
        this.words = puzzleKey.getWordList();
        this.constraints = Constraints.generateConstraints(puzzleKey);
        this.domains = domains;

        puzzleKey.setDomains(domains);
    }

    public CrosswordPuzzle solve() {
        return puzzleKey.createBlankPuzzle();
    }

    public void print() {
        Arrays.stream(words).parallel().forEach(Word::print);
        constraints.stream().parallel().forEach(Constraint::print);
        if (constraints.isEmpty()) System.out.println("Constraints are empty!");
    }
}
