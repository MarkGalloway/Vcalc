package node.vcalc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DivNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public DivNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public int evaluate() {
        VcalcValue left = op1.evaluate();
        VcalcValue right = op2.evaluate();
        
        if(left.isInteger() && right.isInteger()) {
            return left / right;
        }
        else {
            throw new NotImplementedException();
        }
    }
}
