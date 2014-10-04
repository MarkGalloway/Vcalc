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
        
        if(value.isInt()) {
            System.out.println(value.asInt().toString()); //TODO: override vector to string
        }
        else if(value.isVector()) {
            System.out.println(value.asVector().toString()); //TODO: override vector to string
        }
        else {
            throw new RuntimeException("Unable to print unknown type.");
        }
        return null; //TODO, Fix this return value to return something more useful...
    }
}
