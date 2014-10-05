package node.vcalc;

import errors.vcalc.InvalidAssignmentException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class MultNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public MultNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public VcalcValue<?> evaluate() throws InvalidAssignmentException {
        VcalcValue<?> left = op1.evaluate();
        VcalcValue<?> right = op2.evaluate();
        
        if(left.isInt() && right.isInt()) {
            return new VcalcValue<IntType>(new IntType(left.asInt().getValue() * right.asInt().getValue()));
        }
        
        VectorType leftvector = left.promoteToVector(right);
        VectorType rightvector = right.promoteToVector(left);
        
    	VectorType newVector = new VectorType();
    	
        for(int i = 0; i < leftvector.getSize(); i++) {
        	newVector.addElement(leftvector.getElement(i) * rightvector.getElement(i));
        }
        
        return new VcalcValue<VectorType>(newVector);
    }
}
