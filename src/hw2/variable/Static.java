package hw2.variable;

import hw2.Assignment;
import hw2.Word;

public class Static extends VariableOrderer {
    public Static(Assignment assignment, Word[] variables) {
        super(assignment, variables);
    }

    @Override
    public Word getNext() {
        for (Word variable : variables) {
            if (!assignment.containsKey(variable)) {
                return variable;
            }
        }

        throw new IllegalStateException("getNext() called on complete assignment!");
    }
}
