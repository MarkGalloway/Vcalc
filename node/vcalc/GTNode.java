package node.vcalc;

import errors.vcalc.InvalidAssignmentException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class GTNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public GTNode(VcalcNode op1, VcalcNode op2) {
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
            IntType rval = (leftValue > rightValue)? new IntType(1) : new IntType(0);
            return new VcalcValue<IntType>(rval);
        }
        
        VectorType rightVector = null;
        VectorType leftVector = null;
        
        if(!left.isVector() || !right.isVector()) {
            rightVector = right.promoteToVector(left);
            leftVector = left.promoteToVector(right);
        } else {
            rightVector = right.asVector();
            leftVector = left.asVector();
        }
        
        int longerSize = (rightVector.getSize() > leftVector.getSize()) ? rightVector.getSize() : leftVector.getSize();
        int shorterSize = (rightVector.getSize() < leftVector.getSize()) ? rightVector.getSize() : leftVector.getSize();
        VectorType newVector = new VectorType();
        
        for(int i = 0; i < shorterSize; i++) {
            newVector.addElement((leftVector.getElement(i) > rightVector.getElement(i))? 1 : 0);
        }
        
        while(newVector.getSize() < longerSize) {
            newVector.addElement(0);
        }
        
        return new VcalcValue<VectorType>(newVector); 
    }
}
