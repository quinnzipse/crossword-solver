package hw2;

import java.util.Arrays;

public class CSP {
    private final Word[] words;
    private final Dictionaries domains;
    private final Constraints constraints;
    private final PuzzleKey puzzleKey;
    private final static Assignment FAILURE = null;

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

        if (solution == FAILURE) return null;

        CrosswordPuzzle puzzle = puzzleKey.createBlankPuzzle();
        puzzle.fillWords(solution);

        return puzzle;
    }

    private Assignment backtrackingSearch() {
        return backtrack(new Assignment());
    }

    private Assignment backtrack(Assignment assignment) {
        if (assignment.isComplete(words)) return assignment;
        Word variable = selectUnassignedWord(assignment);

        String[] domain = variable.getDomain();
        if (domain == null) return FAILURE;

        for (String value : orderedDomain(domain)) {
            assignment.addAssignment(variable, value);

            if (assignment.isConsistent()) {
                Assignment result = backtrack(assignment);
                if (result != FAILURE) return result;
            }

            assignment.removeAssignment(variable);
        }
        return FAILURE;
    }

    private String[] orderedDomain(String[] domain) {
        // TODO: Implement ordering of the domain.
        return domain;
    }

    private Word selectUnassignedWord(Assignment assignment) {
        // TODO: Implement other orders of selection.
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
