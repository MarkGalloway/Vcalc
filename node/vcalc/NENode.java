package node.vcalc;

public class NENode implements VcalcNode {
    
    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public NENode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public int evaluate() {
        return (op1.evaluate() != op2.evaluate())? 1 : 0;
    }
}
