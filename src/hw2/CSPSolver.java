package hw2;

import java.util.logging.Level;

public class CSPSolver {
    private final CSP csp;
    private ValueOrderer valueOrderer;
    private VariableOrderer variableOrderer;
    private final Assignment assignment = new Assignment();

    public CSPSolver(CSP csp, ValueOrderer.Order valueOrder, VariableOrderer.Order variableOrder) {
        this.csp = csp;
        setValueOrder(valueOrder);
        setVariableOrder(variableOrder);
    }

    public void setValueOrder(ValueOrderer.Order valueOrder) {
        this.valueOrderer = ValueOrderer.getValueOrderer(valueOrder, assignment);
    }

    public void setVariableOrder(VariableOrderer.Order variableOrder) {
        this.variableOrderer = VariableOrderer.getVariableOrderer(variableOrder, assignment, csp.variables);
    }


    private int recursiveCalls = 0;
    private final StringBuilder indent = new StringBuilder();
    private final static Assignment FAILURE = null;

    public CrosswordPuzzle solve() {
        Logger.log(Level.FINER, "Attempting to solve crossword puzzle...\n");
        long startTime = System.currentTimeMillis();

        Assignment solution = backtrackingSearch();

        long time = System.currentTimeMillis() - startTime;

        if (solution == FAILURE) {
            Logger.log(Level.FINE, String.format("FAILED! Found no solution. Took %dms (%d recursive calls)", time, recursiveCalls));
            return null;
        } else {
            Logger.log(Level.FINE, String.format("SUCCESS! Solving took %dms (%d recursive calls)", time, recursiveCalls));
            return csp.getSolutionPuzzle(solution);
        }
    }

    private Assignment backtrackingSearch() {
        recursiveCalls++;

        if (assignment.isComplete(csp.variables)) return assignment;
        Word variable = selectUnassignedVariable();
        Logger.log(Level.FINEST, String.format("%sBranching on %s:", indent, variable));

        String[] domain = variable.getDomain();
        if (domain == null) return FAILURE;

        for (String value : orderedDomain(variable)) {
            assignment.addAssignment(variable, value);

            if (assignment.isConsistent()) {
                Logger.log(Level.FINEST, String.format("%sAssignment { %s = %s } is consistent", indent, variable, value));
                indent.append(" ");
                Assignment result = backtrackingSearch();
                if (result != FAILURE) return result;
            } else {
                Logger.log(Level.FINEST, String.format("%sAssignment { %s = %s } is inconsistent", indent, variable, value));
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
