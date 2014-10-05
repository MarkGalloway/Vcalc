package node.vcalc;

import symbol.vcalc.Scope;
import symbol.vcalc.VcalcValue;

public class AssignmentNode implements VcalcNode {
    private String id;
    private VcalcNode expr;
    private Scope scope;
    
    public AssignmentNode(String id, VcalcNode expr, Scope scope) {
        this.id = id;
        this.expr = expr;
        this.scope = scope;
    }
    
    @Override
    public VcalcValue<?> evaluate() {
    	VcalcValue<?> assignmentValueType = expr.evaluate();
    	
    	if (scope.anyContains(id)) {
    		VcalcValue<?> oldValueType = scope.resolve(id).getValueType();

        	if (assignmentValueType.isInt() && !oldValueType.isInt())
        	       throw new RuntimeException("Invalid assignment of int to vector.");
        	
        	if (assignmentValueType.isVector() && !oldValueType.isVector())
        		throw new RuntimeException("Invalid assignment of vector to int.");
    	}

        scope.assign(id, assignmentValueType);
        return null;
    }
}
