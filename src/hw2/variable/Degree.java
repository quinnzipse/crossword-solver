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
        PriorityQueue<DegVar> orderedDomain = orderDomain();
        return getNext(orderedDomain);
    }

    private PriorityQueue<DegVar> orderDomain() {
        PriorityQueue<DegVar> orderedDomain = new PriorityQueue<>();

        for (Word variable : variables) {
            if (!assignment.isAssigned(variable)) {
                int degree = calculateDegree(variable);
                orderedDomain.add(new DegVar(degree, variable));
            }
        }

        return orderedDomain;
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
            return degree - o.degree;
        }
    }
}
