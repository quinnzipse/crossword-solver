package hw2.variable;

import hw2.Assignment;
import hw2.Constraint;
import hw2.Constraints;
import hw2.Word;

abstract public class VariableOrderer {
    protected Assignment assignment;
    protected Word[] variables;

    abstract public Word getNext();

    public static VariableOrder getOrderByString(String s) {
        return switch (s) {
            case "static" -> VariableOrder.STATIC;
            case "mrv" -> VariableOrder.MOST_RESTRICTED_VARIABLE;
            case "deg" -> VariableOrder.DEGREE;
            case "mrv+deg" -> VariableOrder.MOST_RESTRICTED_VARIABLE_AND_DEGREE;
            default -> throw new IllegalArgumentException("Invalid Argument for Variable Selection: " + s);
        };
    }

    public static VariableOrderer getVariableOrderer(VariableOrder variableOrder, Assignment assignment, Word[] variables) {
        return switch (variableOrder) {
            case STATIC -> new Static(assignment, variables);
            case DEGREE -> new Degree(assignment, variables);
            case MOST_RESTRICTED_VARIABLE_AND_DEGREE -> new MRVAndDegree(assignment, variables);
            case MOST_RESTRICTED_VARIABLE -> new MRV(assignment, variables);
        };
    }

    public VariableOrderer(Assignment assignment, Word[] variables) {
        this.assignment = assignment;
        this.variables = variables;
    }

    protected int calculateMRVScore(Word variable) {
        int remainingValues = 0;

        String[] domain = variable.getDomain();
        for (String value : domain) {
            assignment.addAssignment(variable, value);
            if (variable.isConsistent(assignment)) remainingValues++;
            assignment.removeAssignment(variable);
        }

        return remainingValues;
    }

    protected int calculateDegree(Word variable) {
        int degree = 0;

        Constraints constraints = variable.constraints;
        for (Constraint constraint : constraints) {
            Word otherWord = constraint.getOtherWord(variable);
            if (!assignment.containsKey(otherWord)) degree++;
        }

        return degree;
    }

    abstract static class Variable implements Comparable<Variable> {
        int degree;
        Word word;

        public Variable(int degree, Word word) {
            this.degree = degree;
            this.word = word;
        }
    }
}
