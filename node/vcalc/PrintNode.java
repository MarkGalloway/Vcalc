package node.vcalc;

import symbol.vcalc.VcalcValue;

public class PrintNode implements VcalcNode {
    
    private final VcalcNode expr;
    
    public PrintNode(VcalcNode expr) {
        this.expr = expr;
    }
    

	@Override
    public VcalcValue<?> evaluate() {
        VcalcValue<?> result = expr.evaluate();
        System.out.println(result.value);
        return null; 
    }
}
