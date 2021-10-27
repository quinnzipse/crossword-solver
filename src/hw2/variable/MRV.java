package hw2.variable;

import hw2.Assignment;
import hw2.Word;

import java.util.PriorityQueue;

public class MRV extends VariableOrderer {
    public MRV(Assignment assignment, Word[] variables) {
        super(assignment, variables);
    }

    @Override
    public Word getNext() {
        PriorityQueue<MRVVariable> orderedDomain = orderDomain();
        if (orderedDomain == null) return null;
        return getNext(orderedDomain);
    }

    private PriorityQueue<MRVVariable> orderDomain() {
        PriorityQueue<MRVVariable> orderedDomain = new PriorityQueue<>();

        for (Word variable : variables) {
            if (assignment.isAssigned(variable)) continue;

            int mrvScore = calculateMRVScore(variable);
            if (mrvScore == 0) return null;
            orderedDomain.add(new MRVVariable(mrvScore, variable));
        }

        return orderedDomain;
    }

    private Word getNext(PriorityQueue<MRVVariable> orderedDomain) {
        MRVVariable v = orderedDomain.poll();
        while (v != null && assignment.containsKey(v.word)) {
            v = orderedDomain.poll();
        }

        if (v == null) return null;
        return v.word;
    }

    private static class MRVVariable extends Variable {
        public MRVVariable(int domainSize, Word word) {
            super(domainSize, word);
        }

        @Override
        public int compareTo(Variable o) {
            return degree - o.degree;
        }
    }
}