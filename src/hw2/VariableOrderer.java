package hw2;

// TODO: Create the variable orders
abstract public class VariableOrderer {
    protected Assignment assignment;
    protected Word[] variables;

    abstract public Word getNext();

    public VariableOrderer(Assignment assignment, Word[] variables) {
        this.assignment = assignment;
        this.variables = variables;
    }

    public enum Order {
        STATIC,
        MOST_RESTRICTED_VARIABLE,
        DEGREE,
        MOST_RESTRICTED_VARIABLE_AND_DEGREE
    }

    public static VariableOrderer getVariableOrderer(Order variableOrder, Assignment assignment, Word[] variables) {
        // TODO: Fill this in
        return new Static(assignment, variables);
    }

    public static class Static extends VariableOrderer {
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
}
