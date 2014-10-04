package node.vcalc;

public class IndexNode implements VcalcNode {
    VcalcNode expression;
    VcalcNode index;
    
    public IndexNode(VcalcNode expression, VcalcNode index) {
        this.expression = expression;
        this.index = index;
    }

    @Override
    public int evaluate() {
        
        
        
        return 0;
    }

}
