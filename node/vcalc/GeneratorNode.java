package node.vcalc;

import symbol.vcalc.VcalcValue;

public class GeneratorNode implements VcalcNode {

//    private final VcalcNode id;
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    
    public GeneratorNode(/*VcalcNode id,*/ VcalcNode op1, VcalcNode op2) {
//        this.id = id;
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public VcalcValue evaluate() {
        VcalcValue vecExpr = op1.evaluate();
        
        if(!vecExpr.isVector()) {
            throw new RuntimeException("");
        }
        
        
        return null;
    }

}
