package node.vcalc;

import scope.vcalc.Scope;
import symbol.vcalc.VcalcValue;

public class AssignmentNode implements VcalcNode {
    private String id;
    private VcalcNode expr;
    private Scope scope;
    
    public AssignmentNode(String id, VcalcNode expr ,Scope scope) {
        this.id = id;
        this.expr = expr;
        this.scope = scope;
    }
    
    @Override
    public VcalcValue evaluate() {
        //scope.assign(id, expr.evaluate());
        throw new RuntimeException("Assignment is not implemented.."); //TODO: Hook up with symbol table
    }
}
