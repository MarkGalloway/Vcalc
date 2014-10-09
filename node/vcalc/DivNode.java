package node.vcalc;

import errors.vcalc.InvalidAssignmentException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class DivNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public DivNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public VcalcValue<?> evaluate() throws InvalidAssignmentException {
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
        
        VectorType leftvector = left.promoteToVector(right);
        //divisor should be padded with 1's instead of 0s
        VectorType rightvector = right.promoteToVector(left, 1);
        
    	VectorType newVector = new VectorType();
    	
        for(int i = 0; i < leftvector.getSize(); i++) {
        	int divisor = rightvector.getElement(i);
            if(divisor == 0) {
                throw new RuntimeException("Division by zero is undefined.");
            }
        	newVector.addElement(leftvector.getElement(i) / divisor);
        }
        
        return new VcalcValue<VectorType>(newVector);
    }
}
