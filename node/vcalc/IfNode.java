package node.vcalc;

import errors.vcalc.InvalidAssignmentException;
import symbol.vcalc.VcalcValue;

public class IfNode implements VcalcNode {
    private VcalcNode expression;
    private VcalcNode block;
    
    public IfNode(VcalcNode expression, VcalcNode block) {
        this.expression = expression;
        this.block = block;
    }
    
    @Override
    public VcalcValue<?> evaluate() throws InvalidAssignmentException {
        VcalcValue<?> conditional = expression.evaluate();
        
        if(!conditional.isInt()) {
            throw new RuntimeException("If condition expects integer 1 or 0. " +
                    "Cannot evaluate " + conditional + " as 1 or 0.");
        }
        
        if(conditional.asInt().getValue() != 0) {
            block.evaluate();
        }
        return null; //TODO, Fix this return value to return something more useful...
    }

}
