package node.vcalc;

import errors.vcalc.InvalidAssignmentException;
import symbol.vcalc.VcalcValue;

public class PrintNode implements VcalcNode {
    
    private final VcalcNode expr;
    
    public PrintNode(VcalcNode expr) {
        this.expr = expr;
    }
    

	@Override
    public VcalcValue<?> evaluate() throws InvalidAssignmentException {
        VcalcValue<?> result = expr.evaluate();
        System.out.println(result.value);
        return null; 
    }
}
