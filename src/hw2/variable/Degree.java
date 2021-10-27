package hw2.variable;

import hw2.Assignment;
import hw2.Word;

import java.util.PriorityQueue;

public class Degree extends VariableOrderer {
    public Degree(Assignment assignment, Word[] variables) {
        super(assignment, variables);
    }

    @Override
    public Word getNext() {
        PriorityQueue<DegVar> orderedVariables = orderVariables();
        return getNext(orderedVariables);
    }

    private PriorityQueue<DegVar> orderVariables() {
        PriorityQueue<DegVar> orderedVariables = new PriorityQueue<>();

        for (Word variable : variables) {
            if (!assignment.isAssigned(variable)) {
                int degree = calculateDegree(variable);
                orderedVariables.add(new DegVar(degree, variable));
            }
        }

        return orderedVariables;
    }

    private Word getNext(PriorityQueue<DegVar> orderedDomain) {
        Variable v = orderedDomain.poll();
        while (v != null && assignment.containsKey(v.word)) {
            v = orderedDomain.poll();
        }

        if (v == null) return null;
        return v.word;
    }

    private static class DegVar extends VariableOrderer.Variable {
        public DegVar(int degree, Word word) {
            super(degree, word);
        }

        @Override
        public int compareTo(Variable o) {
            return o.degree - degree;
        }
    }
}
