package node.vcalc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class AddNode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public AddNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public VcalcValue<?> evaluate() {
        VcalcValue<?> left = op1.evaluate();
        VcalcValue<?> right = op2.evaluate();
        
        if(left.isInt() && right.isInt()) {
            return new VcalcValue<IntType>(new IntType(left.asInt().getValue() + right.asInt().getValue()));
        }
        else {
            throw new NotImplementedException(); //TODO: vector subtraction
            /*VectorType newVector = new VectorType();
        	VectorType longerVector = left.getLonger(right.asVector());

            for(Integer i : longerVector.getVector()) {
            	newVector.addElement(vectorExpr.getElement(i));
            }
            
            return new VcalcValue<VectorType>(newVector);*/
        }
    }

}
