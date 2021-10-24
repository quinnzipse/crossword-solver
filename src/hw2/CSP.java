package hw2;

import java.util.Arrays;

public class CSP {
    private final Word[] words;
    private final Dictionaries domains;
    private final Constraints constraints;

    public CSP(PuzzleKey puzzleKey, Dictionaries domains) {
        this.words = puzzleKey.getWordList();
        this.constraints = Constraints.generateConstraints(puzzleKey);
        this.domains = domains;

        puzzleKey.setDomains(domains);
    }

    public CrosswordPuzzle solve() {
        return null;
    }

    public void print() {
        Arrays.stream(words).parallel().forEach(Word::print);
    }
}
