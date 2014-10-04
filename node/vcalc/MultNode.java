package node.vcalc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;

public class MultNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public MultNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public VcalcValue<?> evaluate() {
        VcalcValue<?> left = op1.evaluate();
        VcalcValue<?> right = op2.evaluate();
        
        if(left.isInt() && right.isInt()) {
            return new VcalcValue<IntType>(new IntType(left.asInt().getValue() * right.asInt().getValue()));
        }
        else {
            throw new NotImplementedException(); //TODO: Vector Mult
        }
    }
}
