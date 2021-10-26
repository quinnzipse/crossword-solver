package hw2;

import java.util.logging.Level;

public class CSP {
    private final Word[] variables;
    private final Dictionaries domains;
    private final Constraints constraints;

    private final PuzzleKey puzzleKey;

    public CSP(PuzzleKey puzzleKey, Dictionaries domains) {
        this.variables = puzzleKey.getWordList();
        this.constraints = Constraints.generateConstraints(puzzleKey);
        this.domains = domains;
        this.puzzleKey = puzzleKey;

        Logger.log(Level.FINER, String.format("CSP has %d variables", variables.length));
        Logger.log(Level.FINER, String.format("CSP has %d constrains", constraints.size()));

        setDomains();
        setConstraints();
    }

    private void setDomains() {
        for (Word word : variables) {
            int wordLength = word.getLength();
            word.setDomain(domains.get(wordLength));
        }
    }

    private void setConstraints() {
        for (Constraint constraint : constraints) {
            constraint.getWord1().addConstraint(constraint);
            constraint.getWord2().addConstraint(constraint);
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
