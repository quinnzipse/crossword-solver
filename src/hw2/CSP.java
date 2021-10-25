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

        setDomains();
        setConstraints();
    }

    private void setDomains() {
        for (Word word : words) {
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

    public CrosswordPuzzle solve() {
        Assignment solution = backtrackingSearch();

        CrosswordPuzzle puzzle = puzzleKey.createBlankPuzzle();
        puzzle.fillWords(solution);

        return puzzle;
    }

    private Assignment backtrackingSearch() {
        return backtrack(new Assignment());
    }

    private Assignment backtrack(Assignment assignment) {
        // if assignment is complete, return assignment.
        if (assignment.isComplete(words)) return assignment;
        // Select unassigned Variable
        Word unassignedWord = selectUnassignedWord(assignment);
        // for each value that variable could have...
        for (String value : unassignedWord.getDomain()) {
            // add to the assignment
            assignment.put(unassignedWord, value);

            // if the value is consistent...
            if (assignment.isConsistent()) {
                // result = recur with new assignment
                Assignment result = backtrack(assignment);
                // if the result is not failure, return it.
                if (result != null) return result;
            }

            // otherwise, remove the last assignment
            assignment.remove(unassignedWord);
        }

        // If a solution wasn't found, return failure.
        return null;
    }

    private Word selectUnassignedWord(Assignment assignment) {
        for (Word word : words) {
            if (!assignment.containsKey(word)) {
                return word;
            }
        }

        throw new IndexOutOfBoundsException("Asked for next word in complete assignment");
    }

    public void print() {
        Arrays.stream(words).parallel().forEach(Word::print);
        constraints.stream().parallel().forEach(Constraint::print);
        if (constraints.isEmpty()) System.out.println("Constraints are empty!");
    }
}
