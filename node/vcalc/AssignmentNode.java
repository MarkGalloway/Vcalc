package node.vcalc;

import scope.vcalc.Scope;

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
    public int evaluate() {
        scope.assign(id, expr.evaluate());
        return 0;
    }
}
