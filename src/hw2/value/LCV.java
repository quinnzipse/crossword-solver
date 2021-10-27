package hw2.value;

import hw2.Assignment;
import hw2.Constraint;
import hw2.Constraints;
import hw2.Word;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LCV extends ValueOrderer {
    private final Assignment assignment;
    private static final int NOT_CONSISTENT = -1;

    public LCV(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public String[] order(Word word) {
        PriorityQueue<Value> orderedDomain = new PriorityQueue<>();

        if (word.constraints.isEmpty()) return word.getDomain();

        for (String domainValue : word.getDomain()) {
            assignment.addAssignment(word, domainValue);

            int lcv = getLCV(word);
            if (lcv != NOT_CONSISTENT) {
                orderedDomain.add(new Value(domainValue, lcv));
            }

            assignment.removeAssignment(word);
        }

        return getArray(orderedDomain);
    }

    private int getLCV(Word word) {
        Constraints constraints = word.constraints;
        int constraintFactor = 0;

        for (Constraint constraint : constraints) {
            Word constrainedWord = constraint.getOtherWord(word);

            if (!assignment.isAssigned(constrainedWord)) {
                int constrainedWordDomainSize = getValidDomainSize(constrainedWord, constraint);

                if (constrainedWordDomainSize == 0) {
                    constraintFactor = NOT_CONSISTENT;
                    break;
                }

                constraintFactor += constrainedWordDomainSize;
            }
        }

        return constraintFactor;
    }

    public int getValidDomainSize(Word constrainedWord, Constraint constraint) {
        String[] constrainedWordDomain = constrainedWord.getDomain();

        return (int) Arrays.stream(constrainedWordDomain)
                .filter(value -> {
                    assignment.addAssignment(constrainedWord, value);
                    boolean keep = constraint.isSatisfied(assignment); // LCV
//                        boolean keep = assignment.isConsistent();        // LCV+
                    assignment.remove(constrainedWord);
                    return keep;
                })
                .count();
    }

    private String[] getArray(PriorityQueue<Value> orderedDomain) {
        return orderedDomain.stream().map(value -> value.value).toArray(String[]::new);
    }

    private static class Value implements Comparable<Value> {
        String value;
        int constrainingFactor;

        public Value(String value, int constrainingFactor) {
            this.value = value;
            this.constrainingFactor = constrainingFactor;
        }

        @Override
        public int compareTo(Value o) {
            return o.constrainingFactor - constrainingFactor;
        }
    }
}