package hw2;

import java.util.logging.Level;

public class CSP {
    public final Word[] variables;
    private final Domains domains;
    private final Constraints constraints;

    private final PuzzleKey puzzleKey;

    public CSP(PuzzleKey puzzleKey, Domains domains) {
        this.variables = puzzleKey.wordList;
        this.domains = domains;
        this.puzzleKey = puzzleKey;

        constraints = Constraints.generateConstraints(puzzleKey);

        Logger.log(Level.FINER, String.format("CSP has %d variables", variables.length));
        Logger.log(Level.FINER, String.format("CSP has %d constrains", constraints.size()));

        setDomains();
    }

    private void setDomains() {
        for (Word word : variables) {
            int wordLength = word.length;
            word.setDomain(domains.get(wordLength));
        }
    }

    public CrosswordPuzzle getSolutionPuzzle(Assignment solution) {
        CrosswordPuzzle solvedPuzzle = puzzleKey.createBlankPuzzle();
        solvedPuzzle.fillWords(solution);
        return solvedPuzzle;
    }
}
