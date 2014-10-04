package node.vcalc;

import symbol.vcalc.VcalcValue;

public class PrintNode implements VcalcNode {
    
    private final VcalcNode expr;
    
    public PrintNode(VcalcNode expr) {
        this.expr = expr;
    }
    
    @Override
    public VcalcValue evaluate() {
        VcalcValue value = expr.evaluate();
        System.out.println(value);
        return null; //TODO, Fix this return value to return something more useful...
    }
}
