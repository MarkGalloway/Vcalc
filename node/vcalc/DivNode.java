package node.vcalc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;

public class DivNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public DivNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public VcalcValue<?> evaluate() {
        VcalcValue<?> left = op1.evaluate();
        VcalcValue<?> right = op2.evaluate();
        
        if(left.isInt() && right.isInt()) {
            int dividend = left.asInt().getValue();
            int divisor = right.asInt().getValue();
            if(divisor == 0) {
                throw new RuntimeException("Division by zero is undefined.");
            }
            
            return new VcalcValue<IntType>(new IntType( dividend / divisor ));
        }
        else {
            throw new NotImplementedException(); //TODO: Vector Division
        }
    }
}
