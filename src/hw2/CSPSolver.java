package hw2;

import java.util.logging.Level;

public class CSPSolver {
    private final CSP csp;
    private ValueOrderer valueOrderer;
    private VariableOrderer variableOrderer;
    private int recursiveCalls = 0;
    private final Assignment assignment = new Assignment();
    private final static Assignment FAILURE = null;
    private final static int NANO_TO_MIL = 1000000;

    public CSPSolver(CSP csp, ValueOrderer.Order valueOrder, VariableOrderer.Order variableOrder) {
        this.csp = csp;
        setValueOrder(valueOrder);
        setVariableOrder(variableOrder);
    }

    public void setValueOrder(ValueOrderer.Order valueOrder) {
        this.valueOrderer = ValueOrderer.getValueOrderer(valueOrder, assignment);
    }

    public void setVariableOrder(VariableOrderer.Order variableOrder) {
        this.variableOrderer = VariableOrderer.getVariableOrderer(variableOrder, assignment, csp.getVariables());
    }

    public CrosswordPuzzle solve() {
        Logger.log(Level.FINER, "Attempting to solve crossword puzzle...\n");
        long startTime = System.nanoTime();

        Assignment solution = backtrackingSearch(1);

        long timeInMS = (System.nanoTime() - startTime) / NANO_TO_MIL;

        if (solution == FAILURE) return null;

        Logger.log(Level.FINE, String.format("SUCCESS! Solving took %dms (%d recursive calls)", timeInMS, recursiveCalls));

        return csp.getSolutionPuzzle(solution);
    }

    private Assignment backtrackingSearch(int indent) {
        recursiveCalls++;

        if (assignment.isComplete(csp.getVariables())) return assignment;
        Word variable = selectUnassignedVariable();
        Logger.log(Level.FINEST, String.format("%" + indent++ + "sBranching on %s:", "", variable.toString()));

        String[] domain = variable.getDomain();
        if (domain == null) return FAILURE;

        for (String value : orderedDomain(variable)) {
            assignment.addAssignment(variable, value);

            if (assignment.isConsistent()) {
                Logger.log(Level.FINEST, String.format("%" + indent + "sAssignment { %s = %s } is consistent", "", variable, value));
                Assignment result = backtrackingSearch(++indent);
                if (result != FAILURE) return result;
            } else {
                Logger.log(Level.FINEST, String.format("%" + indent + "sAssignment { %s = %s } is inconsistent", "", variable, value));
            }

            assignment.removeAssignment(variable);
        }
        return FAILURE;
    }

    private String[] orderedDomain(Word variable) {
        return valueOrderer.order(variable);
    }

    private Word selectUnassignedVariable() {
        return variableOrderer.getNext();
    }
}
