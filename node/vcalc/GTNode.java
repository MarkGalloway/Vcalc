package node.vcalc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;

public class GTNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public GTNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public VcalcValue<?> evaluate() {
        VcalcValue<?> left = op1.evaluate();
        VcalcValue<?> right = op2.evaluate();
        
        if(left.isInt() && right.isInt()) {
            int leftValue = left.asInt().getValue();
            int rightValue = right.asInt().getValue();
            IntType rval = (leftValue > rightValue)? new IntType(1) : new IntType(0);
            return new VcalcValue<IntType>(rval);
        }
        else {
            throw new NotImplementedException(); //TODO: Vector greaterthan
        }
    }
}
