package node.vcalc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;

public class SubNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public SubNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public VcalcValue evaluate() {
        VcalcValue left = op1.evaluate();
        VcalcValue right = op2.evaluate();
        
        if(left.isInt() && right.isInt()) {
            return new VcalcValue(new IntType(left.asInt().getValue() - right.asInt().getValue()));
        }
        else {
            throw new NotImplementedException(); //TODO: vector subtraction
        }
    }

}
