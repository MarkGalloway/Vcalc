package node.vcalc;

import symbol.vcalc.VcalcValue;

public class LoopNode implements VcalcNode {
    private VcalcNode expression;
    private VcalcNode block;
    
    public LoopNode(VcalcNode expression, VcalcNode block) {
        this.expression = expression;
        this.block = block;
    }
    
    @Override
    public VcalcValue<?> evaluate() {
        VcalcValue<?> conditional = expression.evaluate();
        
        if(!conditional.isInt()) {
            throw new RuntimeException("Loop condition expects integer 1 or 0. " +
                    "Cannot evaluate " + conditional + " as 1 or 0.");
        }
        
        while(conditional.asInt().getValue() != 0) {
            block.evaluate();
            
            // Evaluate conditional expression again...
            conditional = expression.evaluate();
        }
        return null; //TODO, Fix this return value to return something more useful...
    }

}
