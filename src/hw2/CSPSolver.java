package hw2;

import java.util.logging.Level;

public class CSPSolver {
    private final CSP csp;
    private ValueOrderer valueOrderer;
    private VariableOrderer variableOrderer;
    private final Assignment assignment = new Assignment();
    private final static Assignment FAILURE = null;

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
        Logger.log(Level.FINER, "Attempting to solve crossword puzzle...");
        Assignment solution = backtrackingSearch();

        if (solution == FAILURE) return null;

        return csp.getSolutionPuzzle(solution);
    }

    private Assignment backtrackingSearch() {
        if (assignment.isComplete(csp.getVariables())) return assignment;
        Word variable = selectUnassignedVariable();

        String[] domain = variable.getDomain();
        if (domain == null) return FAILURE;

        for (String value : orderedDomain(variable)) {
            assignment.addAssignment(variable, value);

            if (assignment.isConsistent()) {
                Assignment result = backtrackingSearch();
                if (result != FAILURE) return result;
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
