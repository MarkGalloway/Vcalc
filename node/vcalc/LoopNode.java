package node.vcalc;

public class LoopNode implements VcalcNode {
    private VcalcNode expression;
    private VcalcNode block;
    
    public LoopNode(VcalcNode expression, VcalcNode block) {
        this.expression = expression;
        this.block = block;
    }
    
    @Override
    public int evaluate() {
        while(expression.evaluate() != 0) {
            block.evaluate();
        }
        return 0;
    }

}
