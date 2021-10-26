package hw2;

import java.util.logging.Level;

public class CSP {
    private final Word[] variables;
    private final Dictionaries domains;

    private final PuzzleKey puzzleKey;

    public CSP(PuzzleKey puzzleKey, Dictionaries domains) {
        this.variables = puzzleKey.wordList;
        this.domains = domains;
        this.puzzleKey = puzzleKey;

        Constraints constraints = Constraints.generateConstraints(puzzleKey);

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

    public Word[] getVariables() {
        return variables;
    }

    public CrosswordPuzzle getSolutionPuzzle(Assignment solution) {
        CrosswordPuzzle solvedPuzzle = puzzleKey.createBlankPuzzle();
        solvedPuzzle.fillWords(solution);
        return solvedPuzzle;
    }
}
