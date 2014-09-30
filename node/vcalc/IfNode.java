package node.vcalc;

public class IfNode implements VcalcNode {
    private VcalcNode expression;
    private VcalcNode block;
    
    public IfNode(VcalcNode expression, VcalcNode block) {
        this.expression = expression;
        this.block = block;
    }
    
    @Override
    public int evaluate() {
        if(expression.evaluate() != 0) {
            block.evaluate();
        }
        return 0;
    }

}
