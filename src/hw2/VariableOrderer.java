package hw2;

import java.util.PriorityQueue;

abstract public class VariableOrderer {
    protected Assignment assignment;
    protected Word[] variables;

    abstract public Word getNext();

    public static Order getOrderByString(String s) {
        return switch (s) {
            case "static" -> Order.STATIC;
            case "mrv" -> Order.MOST_RESTRICTED_VARIABLE;
            case "deg" -> Order.DEGREE;
            case "mrv+deg" -> Order.MOST_RESTRICTED_VARIABLE_AND_DEGREE;
            default -> throw new IllegalArgumentException("Invalid Argument for Variable Selection: " + s);
        };
    }

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
        return switch (variableOrder) {
            case STATIC -> new Static(assignment, variables);
            case DEGREE -> new Degree(assignment, variables);
            case MOST_RESTRICTED_VARIABLE_AND_DEGREE -> new MRVAndDegree(assignment, variables);
            case MOST_RESTRICTED_VARIABLE -> new MRV(assignment, variables);
        };
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

    public static class MRV extends VariableOrderer {
        public MRV(Assignment assignment, Word[] variables) {
            super(assignment, variables);
        }

        @Override
        public Word getNext() {
            PriorityQueue<Variable> orderedDomain = new PriorityQueue<>();

            for (Word variable : variables) {
                if (assignment.containsKey(variable)) continue;
                String[] domain = variable.getDomain();
                int remainingValues = 0;
                for (String value : domain) {
                    assignment.addAssignment(variable, value);
                    if (assignment.isConsistent()) remainingValues++;
                    assignment.removeAssignment(variable);
                }
                orderedDomain.add(new Variable(remainingValues, variable));
            }

            return getNext(orderedDomain);
        }

        private Word getNext(PriorityQueue<Variable> orderedDomain) {
            Variable v = orderedDomain.poll();
            while (v != null && assignment.containsKey(v.word)) {
                v = orderedDomain.poll();
            }

            if (v == null) return null;
            return v.word;
        }

        private static class Variable implements Comparable<Variable> {
            int domainSize;
            Word word;

            public Variable(int domainSize, Word word) {
                this.domainSize = domainSize;
                this.word = word;
            }

            @Override
            public int compareTo(Variable o) {
                return domainSize - o.domainSize;
            }
        }
    }

    public static class Degree extends VariableOrderer {
        public Degree(Assignment assignment, Word[] variables) {
            super(assignment, variables);
        }

        @Override
        public Word getNext() {
            PriorityQueue<Variable> orderedDomain = new PriorityQueue<>();

            for (Word variable : variables) {
                if (assignment.containsKey(variable)) continue;
                int degree = 0;
                Constraints constraints = variable.getConstraints();
                for (Constraint constraint : constraints) {
                    Word otherWord = constraint.getOtherWord(variable);
                    if (!assignment.containsKey(otherWord)) degree++;
                }
                orderedDomain.add(new Variable(degree, variable));
            }

            return getNext(orderedDomain);
        }

        private Word getNext(PriorityQueue<Variable> orderedDomain) {
            Variable v = orderedDomain.poll();
            while (v != null && assignment.containsKey(v.word)) {
                v = orderedDomain.poll();
            }
            if (v == null) return null;
            return v.word;
        }

        private static class Variable implements Comparable<Variable> {
            int degree;
            Word word;

            public Variable(int degree, Word word) {
                this.degree = degree;
                this.word = word;
            }

            @Override
            public int compareTo(Variable o) {
                return degree - o.degree;
            }
        }
    }

    public static class MRVAndDegree extends VariableOrderer {
        public MRVAndDegree(Assignment assignment, Word[] variables) {
            super(assignment, variables);
        }

        @Override
        public Word getNext() {
            PriorityQueue<Variable> orderedDomain = new PriorityQueue<>();

            for (Word variable : variables) {
                if (assignment.containsKey(variable)) continue;
                int remainingValues = 0, degree = 0;
                Constraints constraints = variable.getConstraints();
                for (Constraint constraint : constraints) {
                    Word otherWord = constraint.getOtherWord(variable);
                    if (assignment.containsKey(otherWord)) degree++;
                }

                String[] domain = variable.getDomain();
                for (String value : domain) {
                    assignment.addAssignment(variable, value);
                    if (assignment.isConsistent()) remainingValues++;
                    assignment.removeAssignment(variable);
                }

                orderedDomain.add(new Variable(remainingValues, degree, variable));
            }

            return getNext(orderedDomain);
        }

        private Word getNext(PriorityQueue<Variable> orderedDomain) {
            Variable v = orderedDomain.poll();
            while (v != null && assignment.containsKey(v.word)) {
                v = orderedDomain.poll();
            }
            if (v == null) return null;
            return v.word;
        }

        private static class Variable implements Comparable<Variable> {
            int domainSize, degree;
            Word word;

            public Variable(int domainSize, int degree, Word word) {
                this.domainSize = domainSize;
                this.degree = degree;
                this.word = word;
            }

            @Override
            public int compareTo(Variable o) {
                if (domainSize == o.domainSize) return degree - o.degree;
                return domainSize - o.domainSize;
            }
        }
    }
}
