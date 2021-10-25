package hw2;

import java.util.Arrays;
import java.util.PriorityQueue;

public abstract class ValueOrderer {
    abstract public String[] order(Word word);

    public static ValueOrderer getValueOrderer(ValueOrder valueOrder, Assignment assignment) {
        return switch (valueOrder) {
            case STATIC -> new Static();
            case LEAST_CONSTRAINING_VALUE -> new LCV(assignment);
        };
    }

    public static class LCV extends ValueOrderer {
        private final Assignment assignment;
        private static final int NOT_CONSISTENT = -1;

        public LCV(Assignment assignment) {
            this.assignment = assignment;
        }

        @Override
        public String[] order(Word word) {
            PriorityQueue<Value> orderedDomain = new PriorityQueue<>();

            if (word.getConstraints().isEmpty()) return word.getDomain();

            for (String domainValue : word.getDomain()) {
                assignment.addAssignment(word, domainValue);

                int lcv = getLCV(word);
                if (lcv != NOT_CONSISTENT) orderedDomain.add(new Value(domainValue, lcv));

                assignment.removeAssignment(word);
            }

            return getArray(orderedDomain);
        }

        private int getLCV(Word word) {
            Constraints constraints = word.getConstraints();
            int constraintFactor = 0;

            for (Constraint constraint : constraints) {
                Word constrainedWord = constraint.getOtherWord(word);

                if (!assignment.containsKey(constrainedWord)) {
                    int constrainedWordDomainSize = getValidDomainSize(constrainedWord);

                    if (constrainedWordDomainSize == 0) {
                        constraintFactor = NOT_CONSISTENT;
                        break;
                    }

                    constraintFactor += constrainedWordDomainSize;
                }
            }

            return constraintFactor;
        }

        private String[] getArray(PriorityQueue<Value> orderedDomain) {
            return orderedDomain.stream().map(value -> value.value).toArray(String[]::new);
        }

        private int getValidDomainSize(Word constrainedWord) {
            String[] constrainedWordDomain = constrainedWord.getDomain();

            return (int) Arrays.stream(constrainedWordDomain)
                    .filter(value -> {
                        assignment.addAssignment(constrainedWord, value);
                        boolean keep = assignment.isConsistent();
                        assignment.remove(constrainedWord);
                        return keep;
                    })
                    .count();
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
                return constrainingFactor - o.constrainingFactor;
            }
        }
    }

    public static class Static extends ValueOrderer {
        @Override
        public String[] order(Word word) {
            return word.getDomain();
        }
    }
}
