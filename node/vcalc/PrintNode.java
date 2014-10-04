package node.vcalc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PrintNode implements VcalcNode {
    
    private final VcalcNode expr;
    
    public PrintNode(VcalcNode expr) {
        this.expr = expr;
    }
    
    @Override
    public int evaluate() {
        VcalcValue expr = expr.evaluate();
        
        if(expr.isInteger()) {
            System.out.println(expr.asInteger());
        }
        else if(expr.isVector()) {

            VcalcVector = expr.asVector();

        }
        else {
            throw new NotImplementedException();
        }
        return 0; //TODO, Fix this return value to return something more useful...
    }
}
