package hw2;

public class CSPSolver {
    private final CSP csp;
    private ValueOrderer valueOrderer;
    private VariableOrderer variableOrderer;
    private final Assignment assignment = new Assignment();

    public CSPSolver(CSP csp, ValueOrder valueOrder, VariableOrder variableOrder) {
        this.csp = csp;
        setValueOrder(valueOrder);
        setVariableOrder(variableOrder);
    }

    public void setValueOrder(ValueOrder valueOrder) {
        this.valueOrderer = ValueOrderer.getValueOrderer(valueOrder, assignment);
    }

    public void setVariableOrder(VariableOrder variableOrder){
        this.variableOrderer = VariableOrderer.getVariableOrderer(variableOrder, assignment);
    }

}
