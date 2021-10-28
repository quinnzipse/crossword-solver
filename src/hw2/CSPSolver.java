package hw2;

import hw2.value.ValueOrder;
import hw2.value.ValueOrderer;
import hw2.variable.VariableOrder;
import hw2.variable.VariableOrderer;

import java.util.logging.Level;

public class CSPSolver {
    private final CSP csp;
    private ValueOrderer valueOrderer;
    private VariableOrderer variableOrderer;
    private final Assignment assignment = new Assignment();
    private PuzzleGUI gui;
    private final int guiDelay;
    private final boolean useGUI;

    public CSPSolver(CSP csp, ValueOrder valueOrder, VariableOrder variableOrder, int guiDelay) {
        this.csp = csp;
        this.guiDelay = guiDelay;
        this.useGUI = guiDelay != -1;
        setValueOrder(valueOrder);
        setVariableOrder(variableOrder);
    }

    public void setValueOrder(ValueOrder valueOrder) {
        this.valueOrderer = ValueOrderer.getValueOrderer(valueOrder, assignment);
    }

    public void setVariableOrder(VariableOrder variableOrder) {
        this.variableOrderer = VariableOrderer.getVariableOrderer(variableOrder, assignment, csp.variables);
    }

    private int recursiveCalls = 0;
    private final StringBuilder indent = new StringBuilder();
    private final static Assignment FAILURE = null;

    public CrosswordPuzzle solve() {
        Logger.log(Level.FINER, "Attempting to solve crossword puzzle...\n");
        long startTime = System.currentTimeMillis();
        var tempPuzzle = csp.getSolutionPuzzle(assignment);
        if (useGUI) gui = new PuzzleGUI(tempPuzzle, guiDelay);

        Assignment solution = backtrackingSearch();
        if (useGUI) {
            try {
                gui.update(csp.getSolutionPuzzle(solution), null, assignment);
            } catch (Exception e) {
                System.err.println("Exception thrown");
            }
        }

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
        if (variable == null) return FAILURE;

        if (useGUI) {
            try {
                gui.update(csp.getSolutionPuzzle(assignment), variable, assignment);
            } catch (Exception e) {
                System.err.println("Exception thrown");
            }
        }

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
