package hw2.variable;

import hw2.Assignment;
import hw2.Word;

import java.util.PriorityQueue;

public class MRVAndDegree extends VariableOrderer {
    public MRVAndDegree(Assignment assignment, Word[] variables) {
        super(assignment, variables);
    }

    @Override
    public Word getNext() {
        PriorityQueue<MRVDegVar> orderedDomain = orderDomain();
        return getNext(orderedDomain);
    }

    private PriorityQueue<MRVDegVar> orderDomain() {
        PriorityQueue<MRVDegVar> orderedDomain = new PriorityQueue<>();

        for (Word variable : variables) {
            if (assignment.containsKey(variable)) continue;
            int remainingValues, degree;

            remainingValues = calculateMRVScore(variable);
            degree = calculateDegree(variable);
            orderedDomain.add(new MRVDegVar(remainingValues, degree, variable));
        }

        return orderedDomain;
    }

    private Word getNext(PriorityQueue<MRVDegVar> orderedDomain) {
        MRVDegVar v = orderedDomain.poll();
        while (v != null && assignment.containsKey(v.word)) {
            v = orderedDomain.poll();
        }
        if (v == null) return null;
        return v.word;
    }

    private static class MRVDegVar implements Comparable<MRVDegVar> {
        int domainSize, degree;
        Word word;

        public MRVDegVar(int domainSize, int degree, Word word) {
            this.domainSize = domainSize;
            this.degree = degree;
            this.word = word;
        }

        @Override
        public int compareTo(MRVDegVar o) {
            if (domainSize == o.domainSize) return degree - o.degree;
            return domainSize - o.domainSize;
        }
    }
}