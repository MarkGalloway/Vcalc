package node.vcalc;

import errors.vcalc.InvalidAssignmentException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class NENode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public NENode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
   
	@Override
    public VcalcValue<?> evaluate() throws InvalidAssignmentException {
        VcalcValue<?> left = op1.evaluate();
        VcalcValue<?> right = op2.evaluate();
        
        if(left.isInt() && right.isInt()) {
            int leftValue = left.asInt().getValue();
            int rightValue = right.asInt().getValue();
            IntType rval = (leftValue != rightValue)? new IntType(1) : new IntType(0);
            return new VcalcValue<IntType>(rval);
        }
        
        VectorType leftVector = left.promoteToVector(right);
        VectorType rightVector = right.promoteToVector(left);
        
        
        VectorType newVector = new VectorType();
        
        for(int i = 0; i < leftVector.getSize(); i++) {
            newVector.addElement((leftVector.getElement(i) != rightVector.getElement(i))? 1 : 0);
        }
        
        return new VcalcValue<VectorType>(newVector);
    }
}
