package hw2;

import java.util.Arrays;
import java.util.PriorityQueue;

public abstract class ValueOrderer {
    abstract public String[] order(Word word);

    public enum Order {
        STATIC,
        LEAST_CONSTRAINING_VALUE
    }

    public static Order getOrderByString(String orderCode) {
        return switch (orderCode) {
            case "static" -> Order.STATIC;
            case "lcv" -> Order.LEAST_CONSTRAINING_VALUE;
            default -> throw new IllegalArgumentException("Invalid argument value order: " + orderCode);
        };
    }

    public static ValueOrderer getValueOrderer(Order valueOrder, Assignment assignment) {
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
